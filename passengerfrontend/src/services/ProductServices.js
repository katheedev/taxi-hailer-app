
import api_request from "./api_config.js";

class ProductServices {
  getProducts(page = 1, searchQuery = "", district = "", pd_uploaded_by = 'admin', sold = false) {
    return api_request.get(`/product?page=${page}&searchQuery=${searchQuery}&district=${district}&pd_uploaded_by=${pd_uploaded_by}&sold=${sold}`);
  }
  getProductDetails(product_id) {
    return api_request.get(`/product/${product_id}`);
  }
  getProductByUserId(userId) {
    return api_request.get(`/product/user/${userId}`);
  }
  createProduct(data) {
    return api_request.post("/product", data);
  }
  deleteProduct(product_id) {
    return api_request.del(`/product/${product_id}`);
  }
  addBittings(data, product_id) {
    return api_request.put(`/product/bitting/${product_id}`, data);
  }
  acceptBit(bit_id, product_id) {

    return api_request.put(`/product/bitting/accept/${product_id}`, { bit_id: bit_id });

  }

  orderList() {
    return api_request.get(`/order`);
  }
}

export default new ProductServices();