
const form = document.getElementById('loginForm');
const errorDiv = document.getElementById('error');

    form.addEventListener('submit', async (e) => {
    e.preventDefault();
    await login();
});

    async function login() {
    const username = document.getElementById("username").value;
    const password = document.getElementById("password").value;

    // Clear previous errors
    errorDiv.textContent = "";
    errorDiv.style.display = "none";

    // Basic validation
    if (!username || !password) {
    errorDiv.textContent = "Please fill in all required fields";
    errorDiv.style.display = "block";
    return;
}

    // Show loading state
    const submitButton = form.querySelector('button[type="submit"]');
    const originalText = submitButton.textContent;
    submitButton.textContent = "Logging in...";
    submitButton.disabled = true;

    try {
    const response = await fetch("/login", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ username, password })
});

    if (response.status === 200) {
    const token = response.headers.get("Authorization");
    localStorage.setItem("jwt", token);

    // Show success message briefly before redirect
    errorDiv.textContent = "Login successful! Redirecting...";
    errorDiv.style.color = "green";
    errorDiv.style.display = "block";

    setTimeout(() => {
    window.location.href = "/home.html";
}, 1000);
} else {
    const errorText = await response.text();
    errorDiv.textContent = errorText || "Login failed. Please check your credentials.";
    errorDiv.style.color = "#d4004b";
    errorDiv.style.display = "block";
}
} catch (err) {
    console.error("Login error:", err);
    errorDiv.textContent = "Network error. Please try again.";
    errorDiv.style.color = "#d4004b";
    errorDiv.style.display = "block";
} finally {
    // Reset button state
    submitButton.textContent = originalText;
    submitButton.disabled = false;
}
}
