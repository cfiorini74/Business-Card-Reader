# Business Card Reader!

Business card reader developed for programming challenge #1. To run the program, run the .jar file sent, and choose and open a text file which contains test case: i.e. 

testCase1.txt:

```Foobar Technologies
Analytic Developer
Lisa Haung
1234 Sentry Road
Columbia, MD 12345
Phone: 1-410-555-1234
Fax: 410-555-4321
lisa.haung@foobartech.com
```

Then the solution should appear in the window (You may have to expand it manually- sometimes it defaults to the smallest possible size).
To test another input file, simply close and rerun the jar file.

Source code is located inside /src/main/
# Thought Process
In this problem, I am required to parse data from a business card and identify the name, email, and phone number on the card. I assumed that these three pieces of information are to be given on separate lines. 

 1. **Email**: Fairly straightforward, all that was required was to check for a match for a string that contained a period after the "@" character, as well as characters in between and after. An @ character alone indicates that the line likely contains an email, so I felt like this would be enough to deal with false-positives.
 2. **Name**: To check if a line contained a name or not, I used the [Apache OpenNLP](https://opennlp.apache.org/) library and identified what percentage of each string is recognized by the library as a name. A string with a higher percentage identified would be a more ideal candidate for a name.
 3. **Phone Number**: This the trickiest case for me, because I had to differentiate between fax and phone numbers. To do this, I thought that there are several different ways to declare a line as a phone number, i.e. "Phone: 443-223-4455", "Tele: 443-223-4455", or "Mob: 443-223-4455".  However, there is no commonly used abbreviation for "Fax". I checked if a line contained "Fax", and if so, I used the java port of the [fuzzywuzzy](https://www.geeksforgeeks.org/fuzzywuzzy-python-library/) library to check if the string contained one of the many possible abbreviations for "Phone" or "Telephone". This is to handle cases in which someone has the same phone and fax number: i.e. "Fax/Phone: 1-234-567-8912".

