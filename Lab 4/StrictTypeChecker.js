```javascript
"use strict";

function checkVariable(input) {
    switch (typeof input) {
        case "string":
            return "string";
        case "number":
            return "number";
        case "boolean":
            return "boolean";
        case "bigint":
            return "bigint";
        case "undefined":
            return "undefined";
        case "object":
            return "object";
        default:
            return "object";
    }
}

console.log(checkVariable("TechTrend"));
console.log(checkVariable(123));
console.log(checkVariable(true));
console.log(checkVariable(10n));
console.log(checkVariable(undefined));
console.log(checkVariable(null));
console.log(checkVariable({}));
```
