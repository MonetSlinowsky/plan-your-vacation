# plan-your-vacation
Using C4.5 and open-source Trip Advisor data to build a decision tree that recommends vacations for users!

C4_5_main: Implements the C4.5 algorithm and returns the root of the decision tree

Classifier: Creates a decision tree (by calling C4_5_main) and then tests some new cases. This prints the classification process and announces whether the case was classified correctly or not.

Case: Represents a tuple in the data table (a user's feedback on their trip including user city, hotel city, trip type, and rating)

Attribute: Represents a data attribute (a column heading in the data table)- for example, Trip Type. An instatiation of the class stores the attribute's name as well as all the possible values of the attribute (only discrete attributes used)

dataStruc: Initializes and stores all data required to build the decision tree

Node: Represents a node on the decision tree

gainRatioCalc: Calculates the gain ratio of a particular attribute for a specific set of data cases. This info is used to determine which attribute is placed on the decision tree next. 

