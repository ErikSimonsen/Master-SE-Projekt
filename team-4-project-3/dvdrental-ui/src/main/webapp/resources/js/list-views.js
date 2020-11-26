function toggleLoading(event) {
    console.log(event);
    let loader = document.getElementById("filter-loading");
    if (event.status === "begin") {
        loader.style.display = 'block';
    } else {
        loader.style.display = 'none';
    }
}