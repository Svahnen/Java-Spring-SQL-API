function measurementData() {
    return {
        "value": document.getElementsByClassName("postMeasurement")[0].value,
        "type": document.getElementsByClassName("postMeasurement")[1].value,
        "section": document.getElementsByClassName("postMeasurement")[2].value
    }
}

document.getElementsByClassName("postMeasurement")[3].addEventListener("click", function () {
    event.preventDefault()
    let xhr = new XMLHttpRequest()
    let url = location.origin + "/measurement/add"
    xhr.open("POST", url, true)
    xhr.setRequestHeader("Content-Type", "application/json")
    xhr.send(JSON.stringify(measurementData()))
    console.log(JSON.stringify(measurementData()))
    alert("Added")
})


function priceData() {
    return {
        "price": document.getElementsByClassName("postPrice")[0].value,
    }
}

document.getElementsByClassName("postPrice")[1].addEventListener("click", function () {
    event.preventDefault()
    let xhr = new XMLHttpRequest()
    let url = location.origin + "/price/add"
    xhr.open("POST", url, true)
    xhr.setRequestHeader("Content-Type", "application/json")
    xhr.send(JSON.stringify(priceData()))
    console.log(JSON.stringify(priceData()))
    alert("Added")
}) 
