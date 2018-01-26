var fun1 = function (name) {
    print('Hi there from Javascript, ' + name);
    return "greetings from javascript";
};

var fun2 = function (object) {
    print("JS Class Definition: " + Object.prototype.toString.call(object));
};


var TestNashornClass = Java.type('com.fewok.test.nashorn.TestNashorn');

var result = TestNashornClass.fun3('John Doe');
print(result);

TestNashornClass.fun4({
    foo: 'bar',
    bar: 'foo'
});

function Person(firstName, lastName) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.getFullName = function() {
        return this.firstName + " " + this.lastName;
    }
}

var person1 = new Person("Peter", "Parker");
TestNashornClass.fun5(person1);