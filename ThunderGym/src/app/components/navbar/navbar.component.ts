import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { AuthorizzationService } from 'src/app/services/authorizzation.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss']
})
export class NavbarComponent {

constructor(private authSvc:AuthorizzationService){}

isLogged = this.authSvc.isLogged$;
logout(){
  this.authSvc.logout()
}

}
