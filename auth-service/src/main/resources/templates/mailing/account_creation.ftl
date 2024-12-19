<html>
    <head>
        <style >
            <#include "css/style.css">
        </style>
    </head>
    <body>
        <br/>
        <div style="background:white; margin-top:5%; margin-left:10%; margin-right:10%;" >
            <center>
                <h3>
                    <pre style="font-size: 16px;">Bonjour, ${nomComplet}</pre>
                    <h3>Votre compte a été créé !</h3>
                </h3>
            </center>
            <br/>
            <p style="margin-left: 2%; margin-right: 2%;"> Vous pouvez cliquer sur le lien suivant pour vous <a href="${url}">connecter</a> à l'application.</p>
            <br/>
            <br/>
            <p style="margin-left: 2%;">Dans le cas o&ugrave; le lien ne serait pas visible ou utilisable, vous pouvez s&eacute;lectionner le texte ci-dessous et le placer <br/>dans votre barre d'adresse de navigateur :</p><br/>
            <center>
                <pre style="font-size: 12px;">${url}</pre>
            </center>
            <br/>
            <hr/>
        </div>

        <center>
            Ceci est un email généré automatiquement, veuillez ne pas y répondre svp. <br/
            Fazeyna<br/>
        </center>
        <br/>
    </body>
</html>