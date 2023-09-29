import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { ActivatedRoute, Params } from '@angular/router';


import { IclienteWithID } from 'src/app/interfaces/IclienteWithID';
import { IpageableRes } from 'src/app/interfaces/IpageableRes';
import { ClienteNameDTO } from 'src/app/interfaces/clienteNameDTO';
import { DashBoardService } from 'src/app/services/dashBoard.service';

@Component({
  selector: 'app-find-client',
  templateUrl: './find-client.component.html',
  styleUrls: ['./find-client.component.scss']
})
export class FindClientComponent implements OnInit{
  constructor(private dashSvc:DashBoardService, private route:ActivatedRoute){}

  @ViewChild("modalRinnova")
  modalRinnova!:ElementRef
  buttonRinnova:boolean = true

  pageIndex:number=0
  findClienteUrl:string = "http://localhost:8080/api/dashboard/findCliente"
  serverRes!:string
  clienteSelezionato!:IclienteWithID
  pathForApi!:string
  urlToUse:string =`?page=${this.pageIndex}&size=10`


  clienteDTO:ClienteNameDTO= {
    nome:""
  }

  pageable:IpageableRes = {content: []}




  ngOnInit():void {
    this.route.params.subscribe((res:any ) => {
      if(res.path){
        this.pathForApi= "http://localhost:8080/api/dashboard/"
        this.pathForApi = this.pathForApi + res.path;
        this.findClienteUrl =  this.pathForApi
        console.log(this.findClienteUrl+this.urlToUse);
        this.getClienteWithPost(this.findClienteUrl, this.urlToUse)
      }else{
        this.getClienteWithPost(this.findClienteUrl, this.urlToUse)
      }
    })
  }

  getClienteWithPost(firstPartUrl:string, secondPartUrl:string){

     this.dashSvc.getPageableCliente(firstPartUrl + secondPartUrl,this.clienteDTO)
     .subscribe(res => { this.pageable = res}

    )
  }

  getClienteNrefresh(){
    this.getClienteWithPost(this.findClienteUrl, this.urlToUse)

  }

  nextPage():void{
    if(this.pageable.totalPages){

      if(this.pageIndex < this.pageable.totalPages - 1){
        console.log(this.pageable.totalPages);
        this.pageIndex = this.pageIndex + 1
        console.log(this.findClienteUrl);
        this.urlToUse = `?page=${this.pageIndex}&size=10`
        console.log(this.pageIndex);
        this.getClienteWithPost(this.findClienteUrl, this.urlToUse)

      }
    }
  }

  previusPage():void{
    if(this.pageIndex >= 1){
      this.pageIndex = this.pageIndex - 1
      this.urlToUse = `?page=${this.pageIndex}&size=10`
      this.getClienteWithPost(this.findClienteUrl, this.urlToUse)
    }
  }
  openModal(){
    this.dashSvc.modal.open(this.modalRinnova)
    this.buttonRinnova = true
    this.serverRes =""
  }
  rinnovaCliente(id: number, form: NgForm): void {
    this.dashSvc.rinnovaIngressoCliente(id, form.value).subscribe(
      (res) => {
        this.serverRes = "Ingresso rinnovato";
        this.pageIndex = 0;
        this.buttonRinnova = false;
        this.urlToUse = `?page=${this.pageIndex}&size=10`
        console.log(this.findClienteUrl+this.urlToUse);

        this.getClienteWithPost(this.findClienteUrl, this.urlToUse);
      },
      (err) => {
        this.serverRes = "Errore, qualcosa è andato storto";
      }
    );
  }
  selezionaCliente(cliente:IclienteWithID):void{
    this.clienteSelezionato = cliente
  }

}

