<#macro header>
<!DOCTYPE html>
<html lang="en">
<head>
  <title>Kotlin Journal</title>
  <style>
  :root {
    color-scheme: light dark;
    --rad: 1rem;
  }
  input,textarea {
    border-width: 0;
    border-radius: var(--rad, 1rem);
    outline-color: grey;
    outline-style: solid;
    outline-width: 1px;
    padding-inline: 0.9rem;
    padding-block: 0.9rem;
  }
  a {
    text-decoration: none;
    font-weight: 700;
    color: inherit;
  }
  a:hover {
    text-decoration: underline;
    opacity: 0.8;
  }
  </style>
</head>
<body style="text-align: center; font-family: system-ui, sans-serif">
  <img src="/static/ktor_logo.png">
  <h1>Kotlin Ktor Journal</h1>
  <p><i>Powered by Ktor & Freemarker!</i></p>
  <hr>
  <#nested>
  <a href="/">Back to the main page</a>
</body>
</html>
</#macro>