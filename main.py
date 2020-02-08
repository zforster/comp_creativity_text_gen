# Create a String called content and read the entire content of testFile.txt into it.
# Use string processing methods to strip out everything apart from letters and
# spaces, and turn all of the words into lower case. Then, create an array-like
# structure (e.g. an array or ArrayList) called words and put each word into
# words in the same order as in the text file.


class Markov:
    def __init__(self):
        self.content = None
        self.totals = []
        self.words = []
        self.word_count = {}
        self.word_count_inv = {}
        self.read_text()
        self.number_words()
        self.matrix = []
        self.create_matrix()
        self.count_pairs()
        self.prob_matrix = []
        self.create_probability_matrix()
        self.pick_words()


    def read_text(self):
        with open('testFile.txt', 'r') as file:
            data = file.readlines()
        for item in data:
            item = item.lower().replace("\n", '').split(" ")
            for word in item:
                if word != '':
                    self.words.append(word)

    def number_words(self):
        number = 0
        for item in self.words:
            if item not in self.word_count.keys():
                self.word_count[item] = number
                number = number + 1
        self.word_count_inv = {v: k for k, v in self.word_count.items()}

    def create_matrix(self):
        for i in range(0, len(self.word_count.keys())):
            array = []
            for num in range(0, len(self.word_count.keys())):
                array.append(0)
            self.matrix.append(array)

    def count_pairs(self):
        for i in range(0, len(self.words) - 1):
            word_one = self.words[i]
            word_two = self.words[i+1]
            x = self.word_count[word_one]
            y = self.word_count[word_two]
            self.matrix[x][y] = self.matrix[x][y] + 1
        for i in self.matrix:
            count = 0
            for item in i:
                count = count + item
            self.totals.append(count)

    def create_probability_matrix(self):
        for i in range(0, len(self.word_count.keys())):
            array = []
            for num in range(0, len(self.word_count.keys())):
                array.append(0)
            self.prob_matrix.append(array)
        row = 0
        for i in self.prob_matrix:
            for item in range(0, len(i)):
                row_total = self.totals[row]
                prev_val = i[item-1]
                value_at_count_array = self.matrix[row][item]
                i[item] = prev_val + value_at_count_array / row_total
            row = row + 1
        for row in self.prob_matrix:
            row.insert(0, 0)

    def pick_words(self):
        word = 'the'
        rand = random.random()
        print(self.word_count)
        print(self.word_count_inv)
        for row in self.prob_matrix:



# How to read this? Well, the top row represents bigrams beginning with the word “the”.
# Imagine that we pick a random number in the range [0.0,1.0)—e.g. imagine that we pick the
# number 0.76. Now, we work along the row, until we find the first pair of values that our
# random number sits between. 0.76 isn’t between 0.00 and 0.00 (nothing is!), so we don’t
# follow the word “the” with the word “the”. Move along. 0.76 isn’t between 0.00 and 0.50, so
# this time we don’t choose the word “cat”—though, if the random number had been, say, 0.13,
# then we would have done. We move along until we reach the pair 0.50, 1.00—now, 0.76 is
# between those, so we choose the corresponding word: “mat”.

# Implement this process. Start with a starting word to seed the process, for
# example “the”, then loop around for a fixed number of words, choosing each
# word based on the previous word according to the probability table, and
# printing out the words found. You should be able to generate odd but not totally
# ungrammatical sentences like “the mat was where the cat sat on the cat”.

if __name__ == '__main__':
    import random
    m = Markov()
