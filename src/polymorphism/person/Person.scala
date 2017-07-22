package polymorphism.person

//trait similar like interface

trait Person {
  
}

trait Parent extends Person{
  
}

class Female extends Person{
  
}

class Mother extends Female with Parent with Person {
  
}