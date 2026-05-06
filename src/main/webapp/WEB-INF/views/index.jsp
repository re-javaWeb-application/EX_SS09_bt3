<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>RikkeiMall - Theme</title>
    <style>
        :root {
            color-scheme: light dark;
            font-family: Arial, Helvetica, sans-serif;
        }

        body {
            min-height: 100vh;
            margin: 0;
            display: grid;
            place-items: center;
            background: #f6f7fb;
            color: #20242c;
            transition: background 180ms ease, color 180ms ease;
        }

        body.dark {
            background: #111318;
            color: #f3f5f7;
        }

        main {
            width: min(520px, calc(100% - 32px));
            padding: 32px;
            border: 1px solid #d8dde8;
            border-radius: 8px;
            background: #ffffff;
            box-shadow: 0 18px 45px rgba(24, 32, 56, 0.12);
        }

        body.dark main {
            border-color: #303744;
            background: #191d24;
            box-shadow: 0 18px 45px rgba(0, 0, 0, 0.28);
        }

        h1 {
            margin: 0 0 12px;
            font-size: 28px;
            letter-spacing: 0;
        }

        p {
            margin: 0 0 24px;
            line-height: 1.6;
        }

        button {
            min-height: 44px;
            padding: 0 18px;
            border: 0;
            border-radius: 6px;
            background: #2563eb;
            color: #ffffff;
            font-size: 15px;
            font-weight: 700;
            cursor: pointer;
        }

        button:hover {
            background: #1d4ed8;
        }
    </style>
</head>
<body class="${theme}">
<main>
    <h1>RikkeiMall</h1>
    <p>Giao diện hiện tại: <strong>${themeName}</strong>. Lựa chọn này được lưu bằng cookie trong 30 ngày.</p>
    <form action="${pageContext.request.contextPath}/change-theme" method="post">
        <input type="hidden" name="theme" value="${nextTheme}">
        <button type="submit">${buttonText}</button>
    </form>
</main>
</body>
</html>
