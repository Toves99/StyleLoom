/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utils;

/**
 *
 * @author cekesa
 */
public class Response {

    //responses for products
    public static final String DELETE_PRODUCT_SUCCESS_MESSAGE = "Product deleted Successfully";
    public static final String DELETE_PRODUCT_ERROR_MESSAGE = "Error occurred: Product could not be deleted or does not exist";
    public static final String FETCH_PRODUCT_SUCCESS_MESSAGE = "Product fetched Successfully";
    public static final String FETCH_PRODUCT_ERROR_MESSAGE = "Error occurred: Product could not be fetched or does not exist";
    public static final String ADD_PRODUCT_SUCCESS_MESSAGE = "Product added Successfully";
    public static final String ADD_PRODUCT_ERROR_MESSAGE = "Error occurred: Product could not be added";
    public static final String FETCH_ALL_PRODUCT_SUCCESS_MESSAGE = "Products fetched Successfully";
    public static final String FETCH_ALL_PRODUCT_ERROR_MESSAGE = "Error occurred: Products could not be fetched";

    public static final String UPDATE_PRODUCT_SUCCESS_MESSAGE = "Products updated Successfully";
    public static final String UPDATEL_PRODUCT_ERROR_MESSAGE = "Error occurred: Products could not be updated";

    //codes
    public static final int successCode = 200;
    public static final int errorCode = 201;

    // Responses for orders
    public static final String ADD_ORDER_SUCCESS_MESSAGE = "Order added successfully";
    public static final String ADD_ORDER_ERROR_MESSAGE = "Error occurred: Order could not be added";
    public static final String FETCH_ORDER_SUCCESS_MESSAGE = "Order fetched successfully";
    public static final String FETCH_ORDER_ERROR_MESSAGE = "Error occurred: Order could not be fetched";
    public static final String FETCH_ALL_ORDER_SUCCESS_MESSAGE = "All orders fetched successfully";
    public static final String FETCH_ALL_ORDER_ERROR_MESSAGE = "Error occurred: Orders could not be fetched";
    
    public static final String DELETE_ORDER_SUCCESS_MESSAGE = "order deleted successfully";
    public static final String DELETE_ORDER_ERROR_MESSAGE = "Error occurred: Order could not be deleted";

}
