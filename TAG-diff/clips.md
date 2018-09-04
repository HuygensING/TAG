text tokens need to be synced with their text nodes
markup tokens need to be synced with their markup nodes.


Split 1
The model m1 of witness 1 consists of 1 text node t1 containing (De ... toestemming.)
and 1 markup node m1 "s" linking to the text node

The model m2 of witness 2 consists of 2 text nodes, t1:(De ongewisheid!) and t2:(Voor de toestemming.),
and 2 markup nodes "s" (m1,m2), one linking to t1, the other to t2

to get from the m1 to m2, one sequence of graph operations would be:
- create a new textnode t2 containing "Voor de toestemming."
- for all markup nodes m linked to t1 (except m1), link m to t2
- create a new markup node m2 "s" 
- for all parent markup nodes m of m1, make m parent markup of m2 too
- link m2 to t2
- change the content of t1 to "De ongewisheid!"

