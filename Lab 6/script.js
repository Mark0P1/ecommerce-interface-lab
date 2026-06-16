"use strict";

class Product {
    constructor(id, name, price, image) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.image = image;
    }
}

const products = [
    new Product(1, "Wireless Headphones", 2499, "images/headphones.jpg"),
    new Product(2, "Bluetooth Speaker", 1799, "images/speaker.jpg"),
    new Product(3, "Laptop Backpack", 1299, "images/backpack.jpg"),
    new Product(4, "Running Shoes", 2299, "images/shoes.jpg"),
    new Product(5, "Smart Watch", 4899, "images/smartwatch.jpg"),
    new Product(6, "Casual T-Shirt", 599, "images/tshirt.jpg"),
    new Product(7, "Gaming Mouse", 999, "images/mouse.jpg"),
    new Product(8, "Mechanical Keyboard", 2499, "images/keyboard.jpg"),
    new Product(9, "Phone Case", 299, "images/case.jpg"),
    new Product(10, "Power Bank", 1499, "images/powerbank.jpg")
];

let cart = [];

document.body.addEventListener("click", function (event) {
    if (event.target && event.target.getAttribute("data-id")) {
        if (event.target.textContent === "Add to Cart") {
            const id = parseInt(event.target.getAttribute("data-id"));
            const product = products.find(p => p.id === id);

            if (product) {
                cart.push({ ...product, quantity: 1 });

                const card = event.target.parentElement;
                card.classList.add("fade-in");

                setTimeout(() => {
                    card.classList.remove("fade-in");
                }, 400);
            }
        }
    }
});

document.addEventListener("DOMContentLoaded", function () {
    const container = document.querySelector(".product-grid");

    if (container) {
        container.textContent = "";

        products.forEach(product => {
            const card = document.createElement("article");

            const img = document.createElement("img");
            img.src = product.image;
            img.alt = product.name;

            const title = document.createElement("h3");
            title.textContent = product.name;

            const price = document.createElement("p");
            price.textContent = product.price;
            price.classList.add("price");

            const button = document.createElement("button");
            button.textContent = "Add to Cart";
            button.setAttribute("data-id", product.id);

            card.appendChild(img);
            card.appendChild(title);
            card.appendChild(price);
            card.appendChild(button);

            container.appendChild(card);
        });
    }

    renderCart();
    setupCheckout();
    setupAccount();
});

function renderCart() {
    const cartList = document.querySelector(".cart-list");
    const totalDisplay = document.querySelector(".cart-total");

    if (!cartList) return;

    cartList.textContent = "";

    let total = 0;

    cart.forEach(item => {
        const li = document.createElement("li");

        const name = document.createElement("h3");
        name.textContent = item.name;

        const price = document.createElement("p");
        price.textContent = item.price;

        const qty = document.createElement("input");
        qty.type = "number";
        qty.value = item.quantity;
        qty.setAttribute("data-id", item.id);

        qty.addEventListener("input", function () {
            const newQty = parseInt(qty.value);

            cart = cart.map(p => {
                if (p.id === item.id) {
                    return { ...p, quantity: newQty };
                }
                return p;
            }).filter(p => p.quantity > 0);

            renderCart();
        });

        li.appendChild(name);
        li.appendChild(price);
        li.appendChild(qty);

        cartList.appendChild(li);

        total += item.price * item.quantity;
    });

    if (totalDisplay) {
        totalDisplay.textContent = total;
    }
}

function setupCheckout() {
    const form = document.querySelector("#checkoutForm");

    if (!form) return;

    form.addEventListener("submit", function (event) {
        event.preventDefault();

        const fullName = document.querySelector("#fullName");
        const address = document.querySelector("#address");
        const zip = document.querySelector("#zip");
        const payment = document.querySelectorAll("input[name='payment']");
        const messages = document.querySelectorAll(".error-message");

        let valid = true;

        messages.forEach(m => m.textContent = "");

        if (fullName.value === "") {
            fullName.classList.add("error");
            messages[0].textContent = "Required";
            valid = false;
        }

        if (address.value === "") {
            address.classList.add("error");
            messages[1].textContent = "Required";
            valid = false;
        }

        if (zip.value === "" || isNaN(zip.value)) {
            zip.classList.add("error");
            messages[2].textContent = "Invalid ZIP";
            valid = false;
        }

        let paymentSelected = false;

        payment.forEach(p => {
            if (p.checked) paymentSelected = true;
        });

        if (!paymentSelected) {
            messages[3].textContent = "Select payment";
            valid = false;
        }

        if (valid) {
            window.location.href = "thankyou.html";
        }
    });
}

function setupAccount() {
    const currentUser = {
        name: "Juan Dela Cruz",
        orderHistory: [
            {
                id: "1001",
                date: "2026-05-01",
                total: 3299,
                items: ["Headphones", "Case"]
            },
            {
                id: "1002",
                date: "2026-05-10",
                total: 1799,
                items: ["Speaker"]
            }
        ]
    };

    const header = document.querySelector("#accountHeader h1");

    if (header) {
        header.textContent = "Account - " + currentUser.name;
    }

    const summaries = document.querySelectorAll("summary");

    summaries.forEach(summary => {
        summary.addEventListener("click", function () {
            const id = summary.textContent.replace("Order #", "");
            const order = currentUser.orderHistory.find(o => o.id === id);
            const container = summary.parentElement.querySelector(".order-content");

            if (order && container) {
                container.innerHTML =
                    "Date: " + order.date + "<br>" +
                    "Total: ₱" + order.total + "<br>" +
                    "Items: " + order.items.join(", ");
            }
        });
    });
}