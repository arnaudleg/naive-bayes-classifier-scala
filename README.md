# naive-bayes-classifier-scala

A library for classifying text into multiple categories written in Scala 2.9.1-1 http://www.scala-lang.org/.
This library is the scala implementation of https://github.com/alexandru/stuff-classifier.

Currently provided classifiers:

- a [naive bayes classifier](http://en.wikipedia.org/wiki/Naive_Bayes_classifier)

## Usage

```scala
import main.Classifier

val classifier = new Classifier()

// this classifier use word stemming in english by default.
// If you want to use another language, specify it as follows:
val classifier = new Classifier("french")

// If you don't want any stemming, specify it as follows:
val classifier = new Classifier(null)

// also by default, the parsing phase filters out stop words, to
// disable or to come up with your own list of stop words, on a
// classifier instance you can do this:
Tokenizer.stopWords = Array[String]("stop", "word", "list")
 ```

Training the classifier:

```scala
classifier.train("dog", "Dogs are awesome, cats too. I love my dog")
classifier.train("cat", "Cats are more preferred by software developers. I never could stand cats. I have a dog")
classifier.train("dog", "My dog's name is Willy. He likes to play with my wife's cat all day long. I love dogs")
classifier.train("cat", "Cats are difficult animals, unlike dogs, really annoying, I hate them all")
classifier.train("dog", "So which one should you choose? A dog, definitely.")
classifier.train("cat", "The favorite food for cats is bird meat, although mice are good, but birds are a delicacy")
classifier.train("dog", "A dog will eat anything, including birds or whatever meat")
classifier.train("cat", "My cat's favorite place to purr is on my keyboard")
classifier.train("dog", "My dog's favorite place to take a leak is the tree in front of our house")
```

And finally, classifying:

```scala
classifier.classify("This test is about cats.")
//=> cat
classifier.classify("I hate ...")
//=> cat
classifier.classify("The most annoying animal on earth.")
//=> cat
classifier.classify("The preferred company of software developers.")
//=> cat
classifier.classify("My precious, my favorite!")
//=> cat
classifier.classify("Get off my keyboard!")
//=> cat
classifier.classify("Kill that bird!")
//=> cat

classifier.classify("This test is about dogs.")
//=> dog
classifier.classify("Cats or Dogs?") 
//=> dog
classifier.classify("What pet will I love more?")    
//=> dog
classifier.classify("Willy, where the heck are you?")
//=> dog
classifier.classify("I like big buts and I cannot lie.") 
//=> dog
classifier.classify("Why is the front door of our house open?")
//=> dog
classifier.classify("Who is eating my meat?")
//=> dog
```

## License

MIT Licensed. See LICENSE.txt for details.


