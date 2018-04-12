<%@page contentType="text/html; charset=ISO-8859-1" %>
<!DOCTYPE html>
<html>

<head>
	<title>Cadastro de Jogadores</title>
	<link type="text/css" rel="stylesheet" href="css/style.css">
	<script type="text/javascript" src="js/jquery-3.3.1.min.js"></script>
	
	<script>
	
	$(document).ready(function(){
		
		var selected = $('.grupo-class:checked').val();
		
		if(selected == "Liga da Justica"){
			$("body").css("background-image", "url('img/ligaWallpaper.jpg')");		
		}else{
			$("body").css("background-image", "url('img/vingadoresWallpaper.jpg')");	
		}
		
		// ao selecionar um item
		$("input:radio[name='grupo']").click(function() {
		  var $grupo = $(this).val();

		  
		  
		 	
		  	if($grupo == "Liga da Justica"){		  		
		  		$("body").css("background-image", "url('img/ligaWallpaper.jpg')");	
		  	}else{
		  		$("body").css("background-image", "url('img/vingadoresWallpaper.jpg')");	
		  	}
		  
		});
		
	});
	</script>
	
</head>

<body>
	
	<div id="container">
		<img src="img/UOL_logo_Menor.png" alt="UOL" width="200" height="83"> 
		<h3>Cadastro de Jogador</h3>
		
		<form action="PlayerControllerServlet" method="GET">
		
			<input type="hidden" name="command" value="ADD" />
			
			<table id="table-add-player">
				<tbody>
				
				<tr>
					<td colspan="2"  class="alinha-centro"><span class="msg-codinome">${messages.codinome}</span></td>
					</tr>
				
					<tr>
					<td></td>
					<td><span class="error">${messages.name}</span></td>					
					</tr>
					
					<tr>
						<td class="alinha-esquerda"><label>Nome:</label></td>
						<td><input type="text" name="nome" value="${PLAYER_NOME}" size="40" maxlength="30"/></td>
					</tr>
				
					<tr>
					<td></td>
					<td><span class="error">${messages.email}</span></td>					
					</tr>
					
					<tr>
						<td  class="alinha-esquerda"><label>Email:</label></td>
						<td><input type="text" name="email" value="${PLAYER_EMAIL}" size="40" maxlength="50"/></td>
					</tr>
					
					<tr>
					<td></td>
					<td><span class="error">${messages.telefone}</span></td>					
					</tr>
					
					<tr>
						<td  class="alinha-esquerda"><label>Telefone:</label></td>
						<td><input type="text" name="telefone" value="${PLAYER_TEL}" size="40"></td>
					</tr>
					
					<tr><td colspan="2"></td></tr>
					
					<tr>
						<td class="alinha-esquerda"><label>Quero ser do Grupo:</label></td>
						<td>
							<% 
								String grupoAttr = (String) request.getAttribute("PLAYER_GRUPO");
								String checkedAvengers = "", checkedLeague = "";
								
								if(grupoAttr == null || grupoAttr.equals("Vingadores")) checkedAvengers = "checked";
								else if(grupoAttr.equals("Liga da Justica")) checkedLeague = "checked";
							%>
							
							<input type="radio" name="grupo" class="grupo-class" value="Vingadores" <%= checkedAvengers %>><label class="radio-text" >Vingadores</label>
							<input type="radio" name="grupo" class="grupo-class" value="Liga da Justica" <%= checkedLeague %>> <label class="radio-text" >Liga da Justiça</label>	
							
						</td>
					</tr>
					
					<tr><td colspan="2"> 
					</td></tr>
					
					<tr>
		
					<td colspan="2"  class="alinha-centro">
					    <input type="submit" value="Salvar" class="botao" />
					    <input type="button" value="Listar Jogadores"  class="botao" onclick="window.location.href='PlayerControllerServlet'; return false;" />
					</td>
					</tr>
					
				</tbody>
			</table>
			
		</form>
		
		<div style="clear: both;"></div>
	</div>
</body>

</html>