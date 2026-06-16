```javascript id="itemclass01"
class Item {
    #discount = 0.1;

    constructor(name, price) {
        this.name = name;
        this.price = price;
    }

    get finalPrice() {
        return this.price - (this.price * this.#discount);
    }
}

// Test using new keyword
const item1 = new Item("Wireless Headphones", 2499);
const item2 = new Item("Bluetooth Speaker", 1799);

console.log(item1.finalPrice);
console.log(item2.finalPrice);
```
