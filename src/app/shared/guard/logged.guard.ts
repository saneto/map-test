import { CanActivateFn } from '@angular/router';
import { Injectable } from '@angular/core';
import {
	ActivatedRouteSnapshot,
	RouterStateSnapshot,
	Router,
	UrlTree,
} from '@angular/router';
import { AuthService } from '../../shared/services/auth.service';
import { Observable } from 'rxjs';
@Injectable({
	providedIn: 'root',
})

export class LoggedGuard {
	constructor(public authService: AuthService, public router: Router) { }
	canActivate(
		next: ActivatedRouteSnapshot,
		state: RouterStateSnapshot
	): Observable<boolean> | Promise<boolean> | UrlTree | boolean {
		if (this.authService.isLoggedIn === true) {
			this.router.navigate(['account']);
		}
		return true;
	}

};
