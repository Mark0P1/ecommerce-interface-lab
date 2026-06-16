"use strict";

class Product {
    constructor(
        id,
        name,
        description,
        price,
        stockQuantity,
        imageUrl,
        category
    ) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.imageUrl = imageUrl;
        this.category = category;
    }
}

let cart = [];

const API_URL =
    "http://localhost:8080/api/v1/products";

document.addEventListener(
    "DOMContentLoaded",
    () => {

        renderProducts();

        loadCart();
    }
);

async function renderProducts() {

    const container =
        document.querySelector(".product-grid");

    if (!container) {
        return;
    }

    try {

        const response =
            await fetch(API_URL);

        const products =
            await response.json();

        container.textContent = "";

        products.forEach(product => {

            const article =
                document.createElement("article");

            article.classList.add("product-card");

            const image =
                document.createElement("img");

            image.src =
                product.imageUrl || "placeholder.jpg";

            image.alt =
                product.name;

            const title =
                document.createElement("h3");

            title.textContent =
                product.name;

            const description =
                document.createElement("p");

            description.textContent =
                product.description;

            const price =
                document.createElement("p");

            price.classList.add("price");

            price.textContent =
                product.price;

            const stock =
                document.createElement("p");

            stock.textContent =
                "Stock: " +
                product.stockQuantity;

            const button =
                document.createElement("button");

            button.textContent =
                "Add to Cart";

            button.setAttribute(
                "data-id",
                product.id
            );

            article.appendChild(image);
            article.appendChild(title);
            article.appendChild(description);
            article.appendChild(price);
            article.appendChild(stock);
            article.appendChild(button);

            container.appendChild(article);
        });

    } catch (error) {

        console.error(
            "Failed to load products",
            error
        );
    }
}

document.body.addEventListener(
    "click",
    async (event) => {

        if (
            event.target.tagName === "BUTTON" &&
            event.target.hasAttribute("data-id")
        ) {

            const id =
                event.target.getAttribute(
                    "data-id"
                );

            try {

                const response =
                    await fetch(
                        `${API_URL}/${id}`
                    );

                const product =
                    await response.json();

                cart.push({
                    ...product,
                    quantity: 1
                });

                saveCart();

                renderCart();

                const card =
                    event.target.closest(
                        ".product-card"
                    );

                if (card) {

                    card.classList.add(
                        "fade-in"
                    );

                    setTimeout(() => {

                        card.classList.remove(
                            "fade-in"
                        );

                    }, 500);
                }

            } catch (error) {

                console.error(error);
            }
        }
    }
);

function renderCart() {

    const cartList =
        document.querySelector(
            "#cart-items"
        );

    const totalElement =
        document.querySelector(
            "#cart-total"
        );

    if (!cartList) {
        return;
    }

    cartList.textContent = "";

    cart.forEach(
        (item, index) => {

            const li =
                document.createElement(
                    "li"
                );

            const text =
                document.createTextNode(
                    `${item.name} x ${item.quantity}`
                );

            const quantity =
                document.createElement(
                    "input"
                );

            quantity.type =
                "number";

            quantity.min =
                "0";

            quantity.value =
                item.quantity;

            quantity.addEventListener(
                "change",
                () => {

                    const value =
                        parseInt(
                            quantity.value
                        );

                    if (value <= 0) {

                        cart =
                            cart.filter(
                                (_, i) =>
                                    i !== index
                            );

                    } else {

                        item.quantity =
                            value;
                    }

                    saveCart();

                    renderCart();
                }
            );

            li.appendChild(text);
            li.appendChild(quantity);

            cartList.appendChild(li);
        }
    );

    if (totalElement) {

        const total =
            cart.reduce(
                (sum, item) =>
                    sum +
                    item.price *
                    item.quantity,
                0
            );

        totalElement.textContent =
            total.toFixed(2);
    }
}

function saveCart() {

    localStorage.setItem(
        "cart",
        JSON.stringify(cart)
    );
}

function loadCart() {

    const stored =
        localStorage.getItem(
            "cart"
        );

    if (stored) {

        cart =
            JSON.parse(stored);

        renderCart();
    }
}
const token =
    localStorage.getItem(
        "jwt_token"
    );

headers: {
    "Authorization":
    `Bearer ${token}`
}