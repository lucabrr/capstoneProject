

import { AfterContentInit, Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { catchError, throwError } from 'rxjs';
import { Icliente } from 'src/app/interfaces/Icliente';
import { AuthorizzationService } from 'src/app/services/authorizzation.service';
import { DashBoardService } from 'src/app/services/dashBoard.service';

@Component({
  selector: 'app-client-page',
  templateUrl: './client-page.component.html',
  styleUrls: ['./client-page.component.scss']
})
export class ClientPageComponent implements OnInit,AfterContentInit{
  constructor(private authSvc:AuthorizzationService,
    private dashSvc:DashBoardService,
    private router:Router,
    private modal:NgbModal,
    private route:ActivatedRoute){}



@ViewChild("content")
myModal!:ElementRef
 serverRes!:string
@ViewChild("newClienteForm")
 myForm!:NgForm
 @ViewChild("modal2")
 myModal2!:ElementRef

 id:number|null = null

 clienteDaInviare!:Icliente
 clienteBackup!:Icliente

 ngOnInit(): void {
  this.route.params.subscribe((res:any) =>{ this.id = res.id
    if(this.id){
      this.dashSvc.getClienteById("http://localhost:8080/api/dashboard/clienteId/"+ this.id)
      .subscribe(res => {
        this.clienteDaInviare = res
        this.clienteBackup = res
        this.myForm.form.patchValue(this.clienteDaInviare)



      }
      )
    }
  }
  )
}
ngAfterContentInit(): void {

}

  iscriviCliente(form:NgForm){
    this.dashSvc.iscriviNewCliente(form).subscribe(res => {
      this.serverRes = res;
     setTimeout(() => {
      this.modal.dismissAll();
      this.router.navigate(["/dashboard"])
    }, 3000);}, (err)=>{this.serverRes ="Qualcosa è andato storto riprova"}
    )
  }
  open(modal:ElementRef){
    console.log(this.myForm);
    this.modal.open(modal, { backdrop: 'static' })
  }
  addProprieties(){
    if(this.id){
    this.clienteDaInviare = this.myForm.form.value;
    this.clienteDaInviare.id=this.id;
    this.clienteDaInviare.dataIscrizione= this.clienteBackup.dataIscrizione
    this.clienteDaInviare.dataUltimoIngresso = this.clienteBackup.dataUltimoIngresso
    this.clienteDaInviare.ingresso = this.clienteBackup.ingresso
    this.clienteDaInviare.nomeCompleto=this.clienteDaInviare.nome+this.clienteDaInviare.cognome


    console.log(this.clienteDaInviare);
    }
  }
  modificaCliente(){
    this.addProprieties()
    console.log(this.clienteDaInviare);

    this.dashSvc.modificaCliente(this.clienteDaInviare)
    .subscribe(()=>{
      this.serverRes="cliente modificato!"
        setTimeout(() => {
         this.modal.dismissAll();
         this.router.navigate(["/dashboard"])
         }, 2000);
    }, (err)=>{this.serverRes ="Qualcosa è andato storto riprova"}
    )
  }

}
