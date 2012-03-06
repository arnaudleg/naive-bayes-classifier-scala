package scala.test

import org.scalatest.FunSuite
import main.Classifier
import collection.mutable.HashMap

class ClassifierTest extends FunSuite {
  test("wcount should contain the number of times each word appears in a given text.") {
    val classifier = new Classifier()
    classifier.train("dog", "Dogs are awesome, cats too. I love my dog")
    val wcount = HashMap (
      ("dog","dog") -> 2,
      ("dog", "cat") -> 1,
      ("dog", "awesom") -> 1,
      ("dog", "love") -> 1
    )
    assert(classifier.wcount === wcount)
  }

  test("ccount should contain the number of times each category is used to train the classifier.") {
    val classifier = new Classifier()
    classifier.train("dog", "Dogs are awesome, cats too. I love my dog")
    val ccount1 = HashMap (("dog") -> 1)
    assert(classifier.ccount === ccount1)
    classifier.train("dog", "Dogs are awesome, cats too. I love my dog")
    val ccount2 = HashMap (("dog") -> 2)
    assert(classifier.ccount === ccount2)
  }
  
  test("classify should provide the expected category.") {
    val classifier = new Classifier()
    classifier.train("dog", "Dogs are awesome, cats too. I love my dog")
    classifier.train("cat", "Cats are more preferred by software developers. I never could stand cats. I have a dog")
    classifier.train("dog", "My dog's name is Willy. He likes to play with my wife's cat all day long. I love dogs")
    classifier.train("cat", "Cats are difficult animals, unlike dogs, really annoying, I hate them all")
    classifier.train("dog", "So which one should you choose? A dog, definitely.")
    classifier.train("cat", "The favorite food for cats is bird meat, although mice are good, but birds are a delicacy")
    classifier.train("dog", "A dog will eat anything, including birds or whatever meat")
    classifier.train("cat", "My cat's favorite place to purr is on my keyboard")
    classifier.train("dog", "My dog's favorite place to take a leak is the tree in front of our house")

    assert(classifier.classify("This test is about cats.") === "cat")
    assert(classifier.classify("I hate ...") === "cat")
    assert(classifier.classify("The most annoying animal on earth.") === "cat")
    assert(classifier.classify("The preferred company of software developers.") === "cat")
    assert(classifier.classify("My precious, my favorite!") === "cat")
    assert(classifier.classify("Kill that bird!") === "cat")

    assert(classifier.classify("This test is about dogs.") === "dog")
    assert(classifier.classify("Cats or Dogs?") === "dog")
    assert(classifier.classify("What pet will I love more?") === "dog")
    assert(classifier.classify("Willy, where the heck are you?" ) === "dog")
    assert(classifier.classify("I like big buts and I cannot lie.") === "dog")
    assert(classifier.classify("Why is the front door of our house open?") === "dog")
    assert(classifier.classify("Who ate my meat?") === "dog")
  }
}
