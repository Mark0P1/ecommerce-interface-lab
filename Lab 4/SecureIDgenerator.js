```javascript id="m2k9x1"
function generateIDs(count) {
    const result = [];

    for (let i = 0; i < count; i++) {
        if (i === 5) {
            continue;
        }
        result.push("ID-" + i);
    }

    return result;
}

// Test case
console.log(generateIDs(7));
```
