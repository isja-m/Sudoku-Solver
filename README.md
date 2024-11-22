To run the project, first build with
  ./gradlew build

Then run (from src/main/java)
  java sudoku.java

By default the solver solves the following sudoku:
###################
# |7| # | |5#2|9| #
-------------------
# | | # | |6# |8|1#
-------------------
# | |6#8| | # | |3#
###################
# |2| #9| |3# | | #
-------------------
# |8| #2| | #9|1| #
-------------------
# |1| # | | # | | #
###################
# | | # |7| #8|6| #
-------------------
#5| | # | |2# |7| #
-------------------
# |6|4#5| | # |2| #
###################

In order to solve your own sudoku, supply it to the java script in the command line, using the following format (using the above sudoku as an example):
  java sudoku.java 070005290000006081006800003020903000080200910010000000000070860500002070064500020
