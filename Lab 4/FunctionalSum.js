```javascript id="p3sum01"
function calculateTotal(...numbers) {
    if (!numbers.every(num => typeof num === "number")) {
        throw new TypeError("Invalid input: All arguments must be numbers");
    }

    return numbers.reduce((total, num) => total + num, 0);
}

// Test cases
console.log(calculateTotal(1, 2, 3, 4));
console.log(calculateTotal(10, 20, 30));

try {
    console.log(calculateTotal(1, "2", 3));
} catch (error) {
    console.log(error.message);
}
```
