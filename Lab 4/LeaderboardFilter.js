```javascript id="lb4p4ph01"
function getTopScorers(playerList) {
    return playerList
        .filter(player => player.score > 8)
        .map(player => player.name)
        .join(", ");
}

// Test case (up to 10 Filipino names)
const players = [
    { name: "Juan Dela Cruz", score: 10 },
    { name: "Maria Santos", score: 5 },
    { name: "Jose Reyes", score: 12 },
    { name: "Ana Lopez", score: 8 },
    { name: "Miguel Ramos", score: 9 },
    { name: "Carla Garcia", score: 7 },
    { name: "Paolo Mendoza", score: 15 },
    { name: "Liza Fernandez", score: 6 },
    { name: "Mark Villanueva", score: 11 },
    { name: "Angela Cruz", score: 4 }
];

console.log(getTopScorers(players));
```
