# MyApp

Faudra créer un fichier à la racine dans src/environments/environment.ts
Server.js permet d'en generer un dans le cas d'un CI/CD pour ne pas exposer les clés d'accès à fireBase

un exemple de la structure du fichier si il est fait à la main 

export const environment = {
	production: false,
	firebase: {
		apiKey: "lkmdkqsldklkdkdd",
		authDomain: "nom-projetFB.firebaseapp.com",
		projectId: "nom-projetFB",
		storageBucket: "nom-projetFB.appspot.com",
		messagingSenderId: "",
		appId: ""
	}
};