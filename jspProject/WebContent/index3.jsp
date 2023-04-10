<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Main</title>
</head>
<body>
	<table>
		<tr onclick="test();">
			<td>No</td>
			<td>name</td>
		</tr>
		<tbody id="tb">

		</tbody>
	</table>
	<script>
	function onload(){
		
	}
		function test() {
			var ln = document.createElement("tr");
			for (let i = 0; i < 2; i++) { // 사이즈 별로
				let en = document.createElement("td");
				let tn = document.createTextNode("원하는 값");
				en.appendChild(tn);
				ln.appendChild(en);
			}
			document.getElementById("tb").appendChild(ln);
			// tb는 tbody에 id값 걸려있음
		}
	</script>
</body>
</html>