package services;

import enum_choosing_service.ServiceType;

public class EntityServiceSelector {

    public EntityService getService(ServiceType serviceType){
        EntityService entityService = null;
        switch(serviceType){
            case CARD:{
                entityService = new CardService();
                break;
            }
            case FIRM:{
                entityService = new FirmService();
                break;
            }
            case USER:{
                entityService = new UserService();
                break;
            }
            case SELLER:{
                entityService = new SellerService();
                break;
            }
            case PRODUCT:{
                entityService = new ProductService();
                break;
            }
            case SHOP_CART:{
                entityService = new ShopCartService();
                break;
            }
            case SHOP_CLIENT:{
                entityService = new ShopClientService();
                break;
            }
        }
        return entityService;
    }
}
