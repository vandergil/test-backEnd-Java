<%@page contentType="text/html; charset=ISO-8859-1" %>
<!DOCTYPE html>
<html>

<head>
	<title>Atualização de Jogadores</title>
	<link type="text/css" rel="stylesheet" href="css/style.css">
	<script type="text/javascript" src="js/jquery-3.3.1.min.js"></script>	

	<script>	
	$(document).ready(function(){
			
		// Get the value from a dropdown select directly
		$grupo = $('input[name=grupo]').val();
		
		if($grupo == "Liga da Justica"){		  		
	  		$("body").css("background-image", "url('img/ligaWallpaper.jpg')");	
	  	}else{
	  		$("body").css("background-image", "url('img/vingadoresWallpaper.jpg')");	
	  	}
		
	});
	</script>
	
</head>

<body>
	
	<div id="container">
	    <img src="img/UOL_logo_Menor.png" alt="UOL" width="200" height="83"> 
		<h3>Atualizar Jogador</h3>
		
		<form action="PlayerControllerServlet" method="GET">
		
			<input type="hidden" name="command" value="UPDATE" />
			<input type="hidden" name="playerId" value="${THE_PLAYER.id}" />
			
			<input type="hidden" name="grupo" value="${THE_PLAYER.grupo}" />
			<input type="hidden" name="codinome" value="${THE_PLAYER.codinome}" />
			
			<table id="table-add-player">
				<tbody>
					
					<tr>
					<td></td>
					<td><span class="error">${messages.name}</span></td>					
					</tr>
				
					<tr>					
						<td class="alinha-esquerda"><label>Nome:</label></td>
						<td><input type="text" name="nome" value="${THE_PLAYER.nome}" size="40"  maxlength="30"/></td>
					</tr>
				
					<tr>
					<td></td>
					<td><span class="error">${messages.email}</span></td>					
					</tr>

					<tr>				
						<td  class="alinha-esquerda"><label>Email:</label></td>
						<td><input type="text" name="email" value="${THE_PLAYER.email}" size="40"  maxlength="50"/></td>
					</tr>
					
					<tr>
					<td></td>
					<td><span class="error">${messages.telefone}</span></td>					
					</tr>

					<tr>					
						<td  class="alinha-esquerda"><label>Telefone:</label></td>
						<td><input type="text" name="telefone" value="${THE_PLAYER.telefone}" size="40"></td>
					</tr>
					
					<tr><td colspan="2"></td></tr>
					
					<tr>
						<td class="alinha-esquerda">Grupo:</td>
						<td colspan="2"><input type="text" value="${THE_PLAYER.grupo}" size="40" disabled="disabled"></td>
						
					</tr>
					
					<tr><td colspan="2"></td></tr>
					
					<tr>
						<td class="alinha-esquerda">Codinome:</td>
						<td colspan="2"><input type="text" value="${THE_PLAYER.codinome}" size="40" disabled="disabled"></td>
					
					</tr>
					
					<tr><td colspan="2"></td></tr>
						<tr>
		
					<td colspan="2"  class="alinha-centro">
					    <input type="submit" value="Atualizar" class="botao" />
					    <input type="button" value="Listar Jogadores"  class="botao" onclick="window.location.href='PlayerControllerServlet'; return false;" />
					</td>
					</tr>
					
				</tbody>
			</table>
			
		</form>
		<span class="error">${messages.codinome}</span>
		<div style="clear: both;"></div>
	</div>
</body>

</html>