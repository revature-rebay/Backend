/* This Service handles connections to the Cart DB objects
* This also references the UserService for some functionality
* We return an up to date cart for the frontend, as well as invalid
* Items in the checkout method.
* */

package com.revature.service;

import com.revature.models.CartDTO;
import com.revature.models.CartItem;
import com.revature.models.Product;
import com.revature.models.User;
import com.revature.repo.ProductDAO;
import com.revature.repo.UserDAO;
import com.revature.utils.CartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    private UserDAO userDAO;
    private ProductDAO productDAO;

    public CartService() {
    }

    @Autowired
    public CartService(UserDAO userDAO, ProductDAO productDAO) {
        this.userDAO = userDAO;
        this.productDAO = productDAO;
    }


    /*
    * Returns a list of CartItem for the user with the given userId. Returns an empty list if no such user exists
    * @param userId the id of the user whose cart we want
    * @return the list of cartItems a user has in their cart
    * */
    public List<CartItem> getCart(int userId){
        Optional<User> user = userDAO.findById(userId);
        if(user.isPresent()) return user.get().getCart();
        return new ArrayList<>();
    }

    /*
     * Adds a new CartItem to a users cart and returns a list of a users updated cart
     * @param item The CartDTO object with a userId, productId, and quantity. The userId is the id of the user whose cart we want to add the item to.
     * The productId is the id of the product we want to add to the users cart. The quantity is the amount of this product the user wants in their cart.
     * @return the updated list of cartItems a user has in their cart
     * */
    public List<CartItem> addToCart(CartDTO item){

        //User
        Optional<User> userOptional = userDAO.findById(item.userId);
        if(!userOptional.isPresent()){
            return new ArrayList<>();
        }
        User user = userOptional.get();
        ArrayList<CartItem> cartItems = new ArrayList<>(user.getCart());
        //Product
        Optional<Product> productOptional = productDAO.findById(item.productId);
        if(!productOptional.isPresent()){
            return cartItems;
        }
        Product product = productOptional.get();

        for(int i = 0; i <= cartItems.size()-1; i++)
        {
            if(cartItems.get(i).getProduct().getProductId() == item.productId)
            {
                System.out.println("dont add duplicate numbers use the update feature instead");
                return cartItems;
            }
        }


        CartItem cartItem = new CartItem(item.quantity, product, user);

        if(item.quantity > product.getCurrentStock() || item.quantity<0){
            System.out.println("Invalid Quantity.");
            return cartItems;
        }

        //adding
        user.getCart().add(cartItem);
//        user.addCartItem(cartItem);
        userDAO.save(user);
        return user.getCart();
    }



    /*
     * Updates the quantity of a CartItem in a users cart. This only adjusts quantity of existing objects in the cart, and calls delete if 0.
     * @param item The CartDTO object with a userId, productId, and quantity. The userId is the id of the user whose cart we want to add the item to.
     * The productId is the id of the product we want to add to the users cart. The quantity is the amount of this product the user wants in their cart.
     * @return the updated list of cartItems a user has in their cart
     * */
    public List<CartItem> updateProductQuantity(CartDTO item){
        Optional<User> userOptional = userDAO.findById(item.userId);
        if(!userOptional.isPresent()){
            return new ArrayList<>();
        }
        User user = userOptional.get();

        Optional<Product> productOptional = productDAO.findById(item.productId);
        if(!productOptional.isPresent()){
            return user.getCart();
        }
        Product product = productOptional.get();



        if(item.productId < 1 || item.userId < 1 || item.quantity < 0){
            System.out.println("Error, invalid input in update Quantity.");
            return user.getCart();
        }
        if(item.quantity == 0)
            deleteFromCart(item);

        if(item.quantity > product.getCurrentStock()){
            item.quantity = product.getCurrentStock();
        }


        //user.updateCartItem(item);
        user.getCart().replaceAll(cartItem -> {
            if(cartItem.getProduct().getProductId() == item.productId)
            {
                cartItem.setQuantity(item.quantity);
            }
            return cartItem;
        });
        userDAO.save(user);
        return(user.getCart());
    }


    /*
     * Removes a CartItem in a users cart. We use userDAO.save in order to tell Spring to push to the DB. This is a transaction, so we can undo if an exception is throw.
     * @param item The CartDTO object with a userId, productId, and quantity. The userId is the id of the user whose cart we want to add the item to.
     * The productId is the id of the product we want to add to the users cart. The quantity is the amount of this product the user wants in their cart.
     * @return the updated list of cartItems a user has in their cart
     * */
    @Transactional
    public List<CartItem> deleteFromCart(CartDTO item){
        //Get the User and cartItems List to modify
        Optional<User> userOptional = userDAO.findById(item.userId);
        if(!userOptional.isPresent()){
            return null;
        }
        User user = userOptional.get();
        ArrayList<CartItem> cartItems = new ArrayList<>(user.getCart());

        //Get the product to remove
        Optional<Product> productOptional = productDAO.findById(item.productId);
        if(!productOptional.isPresent()){
            return cartItems;
        }
        Product product = productOptional.get();
        CartItem cartItem = new CartItem(0, product, user);
//        user.removeCartItem(cartItem);
        user.getCart().remove(cartItem);

        userDAO.save(user);
        return user.getCart();
    }


    /*
     * Removes all CartItems from a users cart, making it empty.
     * @param userId the id of the user whose cart we want to clear
     * @return true when successful false if a user with the given userId does not exist
     * */
    public boolean clearCart(int userId){
        Optional<User> userOptional = userDAO.findById(userId);
        if(!userOptional.isPresent()){
            return false;
        }

        User user = userOptional.get();
        //user.clearCart();
        user.getCart().clear();
        userDAO.save(user);
        return true;
    }


    /*
     * Checks out all Items from a user's cart, clearing the cart if successful. It also decrements each product's currentStock count when successful. It returns a custom
     * exception so we can store the array and throw the exception. This is a transaction, so we can undo if an exception is throw.
     * @param userId the id of the user whose cart we want to clear
     * @return an empty arraylist when successful. If a user with the given userId does not exist returns null. If a user has no items in their cart returns null.
     * @throws RuntimeException when a user has a product in their cart with a productId that does not exist in the DB
     * @throws CartException when a CartItem in the users cart has a quantity greater than the currentStock of that product. The list of out of stock items is given to the exception and can be
     * retrieved through the exceptions getNotInStock() method.
     * */
    @Transactional
    public List<CartItem> checkout(int userId) {
        Optional<User> userOptional = userDAO.findById(userId);
        if (!userOptional.isPresent()) {
            return null;
        }
        User user = userOptional.get();

        if(user.getCart().isEmpty()) return null;

        ArrayList<CartItem> notInStock = new ArrayList<>();

        for (CartItem c : user.getCart())
        {
            Optional<Product> productOptional = productDAO.findById(c.getProduct().getProductId());
            if(!productOptional.isPresent()){
                //cart contains a product that does not exist in db
                //should probably never happen
                throw new RuntimeException();
            }
            else if(productOptional.get().getCurrentStock() >= c.getQuantity() ) {
                Product product = productOptional.get();
                product.setCurrentStock(product.getCurrentStock()- c.getQuantity());
                productDAO.save(product);
            }
            else{
                notInStock.add(c);
            }
        }

        if(!notInStock.isEmpty()) {
            throw new CartException(notInStock, "Invalid Cart: Product Quantity exceeds stock");
        }
        clearCart(userId);
        return new ArrayList<>();
    }
}
