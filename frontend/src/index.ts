interface Animal {
    name: number
    age: number | string
}

function getAnimalAge(animal : Animal) {
    return animal.age == 5
}


getAnimalAge({name: 23, age: "Manfred"})