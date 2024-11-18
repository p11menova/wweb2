<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.example.wweb2.bean.ResultBean" %>
<%@ page import="com.example.wweb2.bean.Result" %>


<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>NEUROTECH</title>
    <link rel="icon" href="resources/tick.png" type="image/png">
    <%
        ResultBean resultBean = (request.getSession().getAttribute("resultBean") == null)
                ? new ResultBean()
                : (ResultBean) request.getSession().getAttribute("resultBean");
    %>

    <style>
        .parent {
            display: flex;
            text-align: center;
            overflow: hidden; /*внутренние блоки не выходят за пределы*/
            width: 100%;
            background-color: rgba(255, 0, 0, 0.62);
        }


        #input-container {
            padding-top: 20px;
            width: 35%;
            display: inline-block;
            background-color: aliceblue;
        }

        #input-container label {
            font-weight: bolder;
        }

        #submit-button-container {
            padding-top: 20px;
            width: 25%;
            background-color:aliceblue;
            display: table-column;
        }

        .tick-button {
            background-image: url(resources/tick.png);
            background-size: 320px, 320px;
            background-color: aliceblue;
            border: transparent;
            width: 320px;
            height: 320px;
            font-family: 'Impact';
            transition: .8s ease-in-out;
            color: white;
            border-radius: 60px;
        }

        .tick-button:hover {
            background-color: #58d263;
            background-image: none;
            border-radius: 60px;
        }

        #replacement {
            display: none;
            width: 320px;
            height: 320px;
            background-image: url(./resources/boom.png);
            background-size: 340px, 340px;
            font-size: 16px;
            color: red;
            border-radius: 60px;
        }

        #canvas {
            background-color: white;
            border: 10px solid lightcyan;
            padding: 5px;
        }
        .table-container{
            width: 40%;
            background-color: aliceblue;
            float: right;
            display: inline-block;
            text-align: center;
            padding-top: 20px;
        }

        #stats {
            width: 90%;
            border-collapse: separate;
            border-spacing: 4px;
        }

        #stats td {
            padding: 10px;
            background-color: #e1ffff;
            border-radius: 5px;
            color: black;
            font-weight: bold;
            text-align: center;
        }

        .table-stats-header {
            color: white;
            background-color: #58d263;
            text-align: center;
        }

        .table-stats-header-coords {
            width: 60px;
        }


        .padding-column {
            padding: 11px;
        }

        body {
            font-family: 'Arial', sans-serif;
        }
    </style>
</head>
<body>
<div class="header" style="background-color: lightsteelblue; padding: 40px">
    <h2 style="text-align: center"> WEB LAB 2</h2>
    <div style="display: inline-flex; align-items: center;">
        <h3 style="margin: 0 10px 0 0;">ekaterina pim</h3>
        <img src="resources/emojisky.com-5603.png" style="width: 35px; height: 35px;" alt="">
    </div>
    <h3 style="text-align: right">variant 7769</h3>
</div>


<div id="main-container" class="parent">
    <div id="input-container" class="child" style="max-width: 35%">
        <canvas id="canvas" width="450px" height="450px"></canvas>
        <br>
        <table>
            <tr>
                <!-- Координата X -->
                <td class="padding-column" style="vertical-align: top">
                    <label>
                        введите Х координату (от -5 до 5):
                        <br><br>
                        <input type="text" name="x_coord" id="X-input" placeholder="Введите значение от -5 до 5"
                               style="background-color:lightcyan;">
                    </label><br>
                </td>

                <!-- Координата Y -->
                <td class="padding-column" style="text-align: left">
                    <label>
                        введите Y координату (от -2 до 2):
                        <br><br>
                        <label><input type="radio" name="y_coord" value="-2"/> -2</label><br>
                        <label><input type="radio" name="y_coord" value="-1.5"/> -1.5</label><br>
                        <label><input type="radio" name="y_coord" value="-1"/> -1</label><br>
                        <label><input type="radio" name="y_coord" value="-0.5"/> -0.5</label><br>
                        <label><input type="radio" name="y_coord" value="0" checked/> 0</label><br>
                        <label><input type="radio" name="y_coord" value="0.5"/> 0.5</label><br>
                        <label><input type="radio" name="y_coord" value="1"/> 1</label><br>
                        <label><input type="radio" name="y_coord" value="1.5"/> 1.5</label><br>
                        <label><input type="radio" name="y_coord" value="2"/> 2</label><br>
                    </label>
                </td>

                <!-- Радиус R -->
                <td class="padding-column" style="vertical-align: top;">
                    <label>выберите значение радиуса R (от 1 до 5):
                        <br><br>
                        <label><input type="checkbox" name="Radius" value="1" checked/> 1</label><br>
                        <label><input type="checkbox" name="Radius" value="2"/> 2</label><br>
                        <label><input type="checkbox" name="Radius" value="3"/> 3</label><br>
                        <label><input type="checkbox" name="Radius" value="4"/> 4</label><br>
                        <label><input type="checkbox" name="Radius" value="5"/> 5</label><br>
                    </label>
                </td>
            </tr>
        </table>
    </div>

    <!-- Кнопка отправки -->
    <div id="submit-button-container" class="child">
        <button class="tick-button" type="submit" id="tick-button"></button>
        <div id="replacement"></div>
        <h2 style="color: #ff0000" id="error"></h2>
    </div>



    <div id="result" class="table-container">
        <table id="stats">
            <thead>
            <tr>
                <th class="table-stats-header"><h3><em class="table-stats-header-coords"> <i>x</i> </em></h3></th>
                <th class="table-stats-header"><h3><em class="table-stats-header-coords"> <i>y</i> </em></h3></th>
                <th class="table-stats-header"><h3><em class="table-stats-header-coords"> <i>R</i> </em></h3></th>
                <th class="table-stats-header"><h3>Result</h3></th>
                <th class="table-stats-header"><h3>Execution time</h3></th>
                <th class="table-stats-header"><h3>Time</h3></th>
            </tr>
            </thead>


            <!-- jsp code for results rows from resultBean -->

            <tbody>
            <%for (Result result: resultBean.getResults()) { %>
            <tr>
                <td><%=result.getX()%></td>
                <td><%=result.getY()%></td>
                <td><%=result.getR()%></td>
                <td style="text-align: center">
                    <video autoplay loop muted style="width: 50px" src="<%=result.isSuccess() ? "resources/tick_gif.mov": "resources/cross_gif.mov"%>">
                    </video>
                </td>
                <td><%=result.getExecutionTime() + "ms"%></td>
                <td><%=result.getCurrentTime()%></td>
            </tr>
            <%}%>

            </tbody>
        </table>

    </div></div>

<script src="js/main.js"></script>
<script src="js/CanvasDrawer.js"></script>
<script src="js/validation.js"></script>
<script src="js/libs/jquery.min.js"></script>

</body>
</html>
