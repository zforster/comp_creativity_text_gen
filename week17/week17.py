# Firstly, write some code to read in a story, and break it down into individual words by
# splitting it on whitespace, and store each word in an array/arraylist or similar structure
# (this is mostly implemented already in ImproveStory.java). Here is an example story that you can
# use (this can be downloaded as story.txt) from the Moodle page:
import requests

from lxml import html

def read_text():
    words = []
    with open('story.txt', 'r') as file:
        data = file.readlines()
    for item in data:
        item = item.lower().replace("\n", '').replace(".", "").replace("(", "").replace(")", "").replace(",", "").split(" ")
        for word in item:
            if word != '':
                words.append(word)
    return words


def id_adjectives(words):
    adj = []
    matched = []
    with open('adj.txt', 'r') as file:
        data = file.readlines()
    for item in data:
        item = item.lower().replace("\n", '')
        adj.append(item)
    for i in words:
        if i in adj:
            matched.append(i)
    return matched


def get_alt_word(matched):
    replacements = {}
    for i in matched:
        url = "http://bonnat.ucd.ie/jigsaw/index.jsp?q={}".format(i)
        r = requests.get(url)
        tree = html.fromstring(r.content)
        text = tree.xpath('//font/text()')
        alt = []
        for replacement in text:
            if "Input an adjectival property" not in replacement and "as" not in replacement and i not in replacement:
                alt.append(replacement)
        replacements[i] = alt
    return replacements


if __name__ == '__main__':
    import random
    all_words = read_text()
    adjectives = id_adjectives(all_words)
    alternatives = get_alt_word(adjectives)
    with open('story.txt', 'r') as file:
        data = file.readlines()
        new_line = ""
        for i in data:
            new_line = i
            for adj in adjectives:
                try:
                    new_line = new_line.replace(adj, "as {} as {}".format(adj, random.choice(alternatives[adj])))
                except IndexError:
                    pass
            print(new_line)