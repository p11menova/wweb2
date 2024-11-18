document.addEventListener("DOMContentLoaded", function() {
    const tickButton = document.getElementById("tick-button")
    tickButton.addEventListener(('mouseover'), () => {
        tickButton.textContent = "check";
        tickButton.style.fontSize = '400%'

    })
    tickButton.addEventListener(('mouseout'), () => {
        tickButton.textContent = "";
        tickButton.style.fontSize = '100%'
        tickButton.style.fontFamily = 'Impact'
    })

    const canvasDrawer = new CanvasDrawer(sendRequest, validateR, getR);
    canvasDrawer.redrawAll()

    document.querySelectorAll('input[name="Radius"]').forEach(checkbox => {
        checkbox.addEventListener('click', () => {
            canvasDrawer.redrawAll(parseInt(checkbox.value));
        })
    })

    function sendRequest(x1 = null, y1 = null, r1 = null) {
        $.ajax({
            type: "GET",
            url: "/server/controller-servlet",
            data: {
                x: x1,
                y: y1,
                r: r1
            },
            success: function (msg) {
                console.log("received success ", msg)
                processSuccess(msg);
            },
            error: function (msg) {
                console.log("received error ", msg.status)
                processError(msg.responseJSON);
            }
        });
    }

    function processError(msgJson) {
        console.log(msgJson);
        alert(msgJson.error);

        // const errorLabel = document.getElementById("error")
        // errorLabel.textContent = msgJson.error;
    }

    function processSuccess(msgJson1) {
       addRowToStats(msgJson1);
       console.log(msgJson1.x, msgJson1.y)

       canvasDrawer.drawBOOM(Number(msgJson1.x.replaceAll(",", ".")), Number(msgJson1.y.replaceAll(",", ".")));

       setTimeout(() => {
           canvasDrawer.redrawAll(msgJson1.r)
           canvasDrawer.drawPoint(Number(msgJson1.x.replaceAll(",", ".")), Number(msgJson1.y.replaceAll(",", ".")),msgJson1.success === "true")
           canvasDrawer.addPoint(msgJson1)
       }, 350);
    console.log("success");
    }


    // submit results
    document.getElementById('tick-button').addEventListener('click', function(event)  {
        if (validateX() && validateR()) {
            const tickButton = document.getElementById("tick-button")
            const replacement = document.getElementById('replacement');
            tickButton.style.display = 'none';  // Hide button
            replacement.style.display = 'block';

            setTimeout(() => {
                replacement.style.display = 'none';
                tickButton.style.display = 'inline';  // Show button after 5 seconds
            }, 1000);

            let x = getX();
            let y = getY();
            let R = getR();
            console.log("checked values", x, y, R);
            cleanErrorLabel();
            sendRequest(x, y, R);
        }
    })

    function cleanErrorLabel() {
        const errorLabel = document.getElementById("error");
        errorLabel.textContent = ""
    }

    function addRowToStats(data) {
        const table = document.getElementById("stats");
        const newRow = table.insertRow();

        const cellX = newRow.insertCell(0);
        const cellY = newRow.insertCell(1);
        const cellR = newRow.insertCell(2);
        const cellResult = newRow.insertCell(3);
        const cellExecutionTime = newRow.insertCell(4);
        const cellTime = newRow.insertCell(5);


        cellX.textContent = parseFloat(data.x.replaceAll(",", "."));
        cellY.textContent = parseFloat(data.y.replaceAll(",", "."));
        cellR.textContent = parseInt(data.r);
        cellExecutionTime.textContent = Number(data.execution_time).toFixed(2) + "ms";
        cellTime.textContent = data.current_time;

        const video = document.createElement("video");
        video.classList.add("status-video");
        video.width = 50;
        video.loop = true;
        video.autoplay = true;
        video.muted = true;


        if (data.success === "true") {
            video.src = "./resources/tick_gif.mov";
        } else if (data.success === "false") {
            video.src = "./resources/cross_gif.mov";
        }

        cellResult.appendChild(video);
    }

    const rInputs = document.querySelectorAll('input[name="Radius"]');
    rInputs.forEach(checkbox => {
        checkbox.addEventListener('change', function () {
            if (this.checked) {
                rInputs.forEach(otherCheckbox => {
                    if (otherCheckbox !== this) {
                        otherCheckbox.checked = false;  // сброс всех остальных чекбоксов
                    }
                });
            }
        });
    });

    const xText = document.getElementById("X-input");

    xText.addEventListener("focus", validateX);
    xText.addEventListener("input", () => {
        validateX();
        let tmp = xText.value;
        let foundDot = false;
        if (!tmp) return;
        y = "";
        let negative = false;
        for (let i = 0; i < tmp.length; i++) {
            if (tmp[i] === '-' && i === 0) {
                negative = true;
            } else if (tmp[i] === '.' && !foundDot) {
                foundDot = true;
            } else if (isNaN(parseFloat(tmp[i]))) continue;
            y += tmp[i];
        }
        if (y.length > 1 && y[1] !== '.' && y[0] === '0') {
            y = y.substring(1);
        }
        while (y.includes('-0') && !y.includes('.')) {
            y = y.replace('-0', '-');
        }
        xText.value = y;
    });

    function getX() {
        const xInput = document.querySelector('input[name="x_coord"]');
        const xValue = xInput.value.trim();

        if (xValue == null) return "";

        return xValue
    }

    function getY() {
        const yRadio = document.querySelectorAll('input[name="y_coord"]');
        res = "";
        yRadio.forEach(radio => { if (radio.checked) res = radio.value})
        return res;
    }

    function getR() {
        let result = "";
        document.querySelectorAll('input[name="Radius"]').forEach(checkbox => {
            if (checkbox.checked) {
                result = checkbox.value;
            }
        })
        return result
    }
})
console.log('Happy developing ✨')

