import {  Component, OnInit } from '@angular/core';
import { AuthorizzationService } from 'src/app/services/authorizzation.service';
import { DashBoardService } from 'src/app/services/dashBoard.service';

@Component({
  selector: 'app-dashboard-page',
  templateUrl: './dashboard-page.component.html',
  styleUrls: ['./dashboard-page.component.scss']
})
export class DashboardPageComponent implements OnInit {
numeroTotaleClienti!:number
numeroTotaleValidClienti!:number
numeroTotaleActiveClienti!:number
numeroTotaleInactiveClienti!:number
importoMeseCorrente:number = 0
importoMeseScorso:number = 0
colorPercentualeFatturato!:string
colorPercentualeIscrizioni!:string
iscrittiMeseCorrente!:number
iscrittiMeseScorso!:number
percentualeIscritti:string | number = 0
percentualeFatturato:string | number = 0





constructor(private authSvc:AuthorizzationService, private dashSvc:DashBoardService){}
ngOnInit(): void {

  this.dashSvc.getNumberOfClienti().subscribe(res => this.numeroTotaleClienti = res)
  this.dashSvc.getNumberOfValidClienti().subscribe(res=> this.numeroTotaleValidClienti = res)
  this.dashSvc.getNumberOfActiveClienti().subscribe(res=> this.numeroTotaleActiveClienti = res)
  this.dashSvc.getInactiveClienti().subscribe(res =>this.numeroTotaleInactiveClienti= res.length )
  this.dashSvc.getAllImporti().subscribe(res =>
    {this.importoMeseCorrente = res.importoMeseCorrente;
       this.importoMeseScorso = res.importoMeseScorso;
         this.percentualeFatturato = this.calcolaPercentuale(this.importoMeseCorrente, this.importoMeseScorso);
        })
  this.dashSvc.getIscritti().subscribe(res =>
    {this.iscrittiMeseCorrente = res.iscrittiMeseCorrente;
       this.iscrittiMeseScorso = res.iscrittiMeseScorso;
       this.percentualeIscritti = this.calcolaPercentuale(this.iscrittiMeseCorrente , this.iscrittiMeseScorso , "iscritti");
      })



}



  calcolaPercentuale(importoCorrente:number, ImportoPassato:number, x?:string):string | number{
    let differenza :number = importoCorrente - ImportoPassato;
    let percentuale:number | string = (differenza / ImportoPassato)* 100;

    if(differenza > 0 ){
      percentuale = "+ " + percentuale.toFixed(1).toString()
      if(x){
        this.colorPercentualeIscrizioni = "green"
      }else{

        this.colorPercentualeFatturato = "green"
      }
    }else if (differenza < 0 ){
      percentuale = "- " + Math.abs(percentuale).toFixed(1).toString()
      if(x){
        this.colorPercentualeIscrizioni = "red"
      }else{
      this.colorPercentualeFatturato = "red"}
    }
    return percentuale;

  }

}
