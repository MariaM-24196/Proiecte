<html>
    <head>
        <title>
            Simple Calculator
        </title>
    </head>
    <body>
        <h3>Simple Calculator</h3>
        <form method=post action="Calc.php">
            <input type="text" name="t1" value="
                <?php 
                    $t1=@$_POST["t1"]; 
                    echo $t1;
                ?>"> 
            <input type="text" size=1 maxlength=1 value="
                <?php
                    $op=@$_POST["op"];
                    if($op=="op1") echo "+";
                    if($op=="op2") echo "-";
                    if($op=="op3") echo "*";
                    if($op=="op4") echo "/";
                ?>" disabled>
            <input type="text" name="t2" value="
                <?php 
                    $t2=@$_POST["t2"]; 
                    echo $t2;
                ?> ">
            <input type="submit" value=" = ">
            <input type="text" name=rezultat value="
                <?php
                    $t1=@$_POST["t1"];
                    $t2=@$_POST["t2"];
                    $op=@$_POST["op"];
                    if($op=="op1") echo $t1+$t2;
                    if($op=="op2") echo $t1-$t2;
                    if($op=="op3") echo $t1*$t2;
                    if($op=="op4") 
                    {
                        if($t2==0) echo "Cannot divide by zero";
                        else echo $t1/$t2;
                    }
                ?> " disabled>
            <br>
            <input type="radio" name="op" value="op1"> +
            <br>
            <input type="radio" name="op" value="op2"> -
            <br>
            <input type="radio" name="op" value="op3"> *
            <br>
            <input type="radio" name="op" value="op4"> /

        </form>
    </body>
</html>