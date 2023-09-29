import { AuthorizzationService } from 'src/app/services/authorizzation.service';
import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, CanActivateChild, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { Observable, map } from 'rxjs';

@Injectable({
  providedIn: 'root'
})

export class DashboardGuard implements CanActivate, CanActivateChild {
  constructor(private authSvc:AuthorizzationService, private router:Router){}
  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
      return this.authSvc.isLogged$.pipe(map((isloggedIn)=>{
        if(isloggedIn){return true}
        else{
          this.router.navigate([""])
          return false
        }
      }))
    }

  canActivateChild(
    childRoute: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    return this.canActivate(childRoute,state)
  }

}
