#[Surf](https://github.com/nhays89/Surf/tree/master/Surf/img)

Division 2 ACM ICPC programming problem. 

<p>

<img src="https://github.com/nhays89/Surf/blob/master/Surf/img/Surf.PNG">

</p>

##Problem
Now that you’ve come to Florida and taken up surfing, you love it! Of course, you’ve realized that
if you take a particular wave, even if it’s very fun, you may miss another wave that’s just about
to come that’s even more fun. Luckily, you’ve gotten excellent data for each wave that is going to
come: you’ll know exactly when it will come, how many fun points you’ll earn if you take it, and
how much time you’ll have to wait before taking another wave. (The wait is due to the fact that
the wave itself takes some time to ride and then you have to paddle back out to where the waves
are crashing.) Obviously, given a list of waves, your goal will be to maximize the amount of fun
you could have.
Consider, for example, the following list of waves:
<table>
  <tr>
    <th align="left">Minute</th>
    <th align="left">Fun Points</th>
    <th align="left">Wait Time</th>
  </tr>
  <tr>
    <td>2</td>
    <td>80</td>
    <td>9</td>
  </tr>
   <tr>
    <td>8</td>
    <td>50</td>
    <td>2</td>
  </tr>
   <tr>
    <td>10</td>
    <td>40</td>
    <td>2</td>
  </tr>
   <tr>
    <td>13</td>
    <td>20</td>
    <td>5</td>
  </tr>
</table>
<section> In this example, you could take the waves at times 8, 10 and 13 for a total of 110 fun points. If
you take the wave at time 2, you can’t ride another wave until time 11, at which point only 20 fun
points are left for the wave at time 13, leaving you with a total of 100 fun points. Thus, for this
input, the correct answer (maximal number of fun points) is 110.
<br>Given a complete listing of waves for the day, determine the maximum number of fun points
you could earn.
</section>

###Input###
___
The first line of input contains a single integer <em>n</em> (1 ≤ <em>n</em> ≤ 300,000), representing the total number
of waves for the day. The <em>i</em>th line (1 ≤ <em>i</em> ≤<em> n</em>) that follows will contain three space separated
integers: <em> m<sub>i</sub></em>,<em> f<sub>i</sub></em>, and <em>w<sub>i</sub></em>, (1 ≤<em> m<sub>i</sub></em>,<em> f<sub>i</sub></em>, <em>w<sub>i</sub></em> ≤ 106), representing the time, fun points, and wait time
of the <em>i</em>th wave, respectively. You can ride another wave occurring at exactly time <em>m<sub>i</sub></em> + <em>w<sub>i</sub></em> after
taking the <em>i</em>th wave. It is guaranteed that no two waves occur at the same time. The waves may
not be listed in chronological order

###Output###
___
Print, on a single line, a single integer indicating the maximum amount of fun points you can get
riding waves.
<table>
  <tr>
    <th align="left" bgcolor="#FFFFFF">
    Sample Input
    </th>
    <th align="left" bgcolor="#HHHHHH">
    Sample Output
    </th>
  </tr>
  <tr>
    <td width="300px">
    4
   <br> 8 50 2
   <br> 10 40 2
   <br> 2 80 9
   <br> 13 20 5
    </td>
    <td width="300px" valign="top">
    110
    </td>
  </tr>
</table>
<table>
  <tr>
    <th align="left" bgcolor="#FFFFFF">
    Sample Input
    </th>
    <th align="left" bgcolor="#HHHHHH">
    Sample Output
    </th>
  </tr>
  <tr>
    <td width="300px">
    10
    <br>2079 809484 180
    <br>8347 336421 2509
    <br>3732 560423 483
    <br>2619 958859 712
    <br>7659 699612 3960
    <br>7856 831372 3673
    <br>5333 170775 1393
    <br>2133 989250 2036
    <br>2731 875483 10
    <br>7850 669453 842
    </td>
    <td width="300px" valign="top">
    3330913
    </td>
  </tr>
</table>

#Solution

The key to solving this problem is to leverage principles of dynamic programming, and make use of efficient data structures. 
####Approach####
____
At first glance, the problem structure resembles [group interval scheduling maximization problem (GISMP)](https://en.wikipedia.org/wiki/Interval_scheduling). 
In these types of problems the goal is to find the largest compatible set — a set of non-overlapping representatives of maximum size. Essentially,
we need to find a set of waves that do not overlap with each other, and produce the maximum possible amount of fun. In this scenario, 
we have overlapping waves that have variable start times, point totals, and durations; therefore, it makes sense to store information about the 
best waves to take as we traverse the set of waves given. It would seem as though we would solve this problem by storing the
maximum totals for each period of time, but in fact, this was not the case—at least for us it wasn't. We noticed that storing
maximums for each wave was crucial, so instead of storing maximums for each period of time, we stored maximums for each new wave we 
encountered. There are two maximums that we needed to store for each new wave: 1) actual, and 2) total. The <em>actual</em> maximum represents the
accumulated point total that can actually be achieved if this wave is choosen. The <em>total</em> maximum represents the accumulated point 
total that is the largest amongst all possible combinations of waves up to this wave. 

####General Idea####
___

The algorithm we choose to implement goes something like this:
<ol>
  <li>For each line in the input data:
    <ol>
      <li> Create a Wave object (start time, fun points, and duration).</li>
      <li> Add this Wave to an ArrayList of Wave's.</li>
    </ol>
  </li>
  <li>Sort the ArrayList by the Wave's start time (when the wave crashes).</li>
  <li>For each Wave in the list:
    <ol>
      <li>Add this Wave to a Priority Heap (lowest end time first).</li>
      <li>For each Wave in the heap that ends (start time + duration) before this Wave begins (non overlapping):
        <ol>
          <li>Remove it from the heap.</li>
          <li>Compare its <em>actual</em> max against the local <em>actual</em> max.</li>
          <li>If its <em>acutal</em> max > local <em>actual</em> max ? update local <em>actual</em> max : continue</li>
        </ol>
      </li>
      <li>Set the global <em>actual</em> max = local <em>actual</em> max</li>
      <li>Set this Wave's <em>actual</em> max = its fun points + global <em>actual</em> max.</li>
      <li>If this waves <em>actual</em> max  > previous wave <em>total</em> max ? set this wave <em>total</em> max = its <em>actual</em> max :
      set this waves <em>total</em> max = previous waves <em>total</em> max.
      </li>
    </ol>
  </li>
  <li>Return the last wave <em>total</em> max.</li>
</ol>


<em>2015 Pacific Northwest Region Programming Contest—Division 2</em>
