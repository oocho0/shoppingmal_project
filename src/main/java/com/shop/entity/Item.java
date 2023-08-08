package com.shop.entity;

import com.shop.constant.ItemSellStatus;
import com.shop.dto.ItemFormDto;
import com.shop.exception.OutOfStockException;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "item")
@Getter
@Setter
@ToString
public class Item extends BaseEntity{

    @Id
    @Column(name = "item_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false, length = 50)
    private String itemName;

    @Column(name = "price", nullable = false)
    private int price;

    @Column(nullable = false)
    private int stockAmount;

    @Lob
    @Column(nullable = false)
    private String itemDetail;

    @Lob
    private String itemRequest;

    @Enumerated(EnumType.STRING)
    private ItemSellStatus itemSellStatus;

    public void updateItem(ItemFormDto itemFormDto) {
        this.itemName = itemFormDto.getItemName();
        this.price = itemFormDto.getPrice();
        this.stockAmount = itemFormDto.getStockAmount();
        this.itemDetail = itemFormDto.getItemDetail();
        this.itemRequest = itemFormDto.getItemRequest();
        this.itemSellStatus = itemFormDto.getItemSellStatus();
    }

    public void removeStock(int stockAmount){
        int restStock = this.stockAmount - stockAmount;
        if(restStock < 0){
            throw new OutOfStockException("상품의 재고가 부족 합니다. (현재 재고 수량 : "+this.stockAmount+")");
        }
        this.stockAmount = restStock;
    }
}
