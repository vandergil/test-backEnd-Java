<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>

<head>
	<title>Jogadores Cadastrados</title>
	<link type="text/css" rel="stylesheet" href="css/style-list-player.css">
	
	<script type="text/javascript" src="js/jquery-3.3.1.min.js"></script>
	<script type="text/javascript" src="js/jquery.tablesorter.min.js"></script>
	<script>
	$(document).ready(function() 
	    { 
	        $("#myTable").tablesorter(); 
	    } 
	); 
	</script>
	
</head>

<body>

	<div id="container">
	<img src="img/UOL_logo_Menor.png" alt="UOL" width="200" height="83"> 
		<div id="content">
		
			
			<h3>Jogadores Cadastrados</h3>
			
			<input type="button" value="Novo Jogador" onclick="window.location.href='add-player-form.jsp'; return false;"	/>
			<br><br>
			<table id="myTable" class="tablesorter">
			<thead> 
		
				<tr>
					<th>Nome</th>
					<th>Email</th>
					<th>Telefone</th>
					<th>Codinome</th>
					<th>Grupo</th>
					<th></th>
				</tr>
			</thead> 
			<tbody> 	
				<c:forEach var="tempPlayer" items="${PLAYER_LIST}">

					<c:url var="tempLink" value="PlayerControllerServlet">
						<c:param name="command" value="LOAD" />
						<c:param name="playerId" value="${tempPlayer.id}" />
					</c:url>

					<!--  set up a link to delete a student -->
					<c:url var="deleteLink" value="PlayerControllerServlet">
						<c:param name="command" value="DELETE" />
						<c:param name="playerId" value="${tempPlayer.id}" />
					</c:url>
																		
					<tr>
						<td> ${tempPlayer.nome} </td>
						<td> ${tempPlayer.email} </td>
						<td> ${tempPlayer.telefone} </td>
						<td> ${tempPlayer.codinome} </td>
						<td> ${tempPlayer.grupo} </td>
						<td> 
							<a href="${tempLink}"><img src="img/update-icon.png" width="22" height="22"></a> 
							&nbsp;
							<a href="${deleteLink}"
							onclick="if (!(confirm('Tem certeza de que deseja apagar este jogador?'))) return false">
							<img src="img/delete-icon.png" width="19" height="19"></a>	
						</td>
					</tr>
				
				</c:forEach>
			<tbody> 	
			</table>
		
		</div>
	
	</div>
</body>


</html>

