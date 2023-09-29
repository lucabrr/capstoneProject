import { Icliente } from './../interfaces/Icliente';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';


import { ItotaleImporti } from '../interfaces/ItotaleImporti';
import { ITotaleIscritti } from '../interfaces/ITotaleIscritti';
import { NgForm } from '@angular/forms';
import { Observable } from 'rxjs';
import { IpageableRes } from '../interfaces/IpageableRes';
import { ClienteNameDTO } from '../interfaces/clienteNameDTO';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

@Injectable({
  providedIn: 'root'
})
export class DashBoardService {
  clientiUrl:string ="http://localhost:8080/api/dashboard/allClienti"
  numberOfClientiUrl:string = "http://localhost:8080/api/dashboard/nClienti"
  numberOfValidClientiUrl:string="http://localhost:8080/api/dashboard/validClienti"
  numberOfActiveClientiUrl:string="http://localhost:8080/api/dashboard/activeClienti"
  inactiveClientiUrl:string="http://localhost:8080/api/dashboard/inactiveClienti"
  totalImportiUrl:string = "http://localhost:8080/api/dashboard/importi"
  totalIscrittiUrl:string = "http://localhost:8080/api/dashboard/iscritti"
  newClienteUrl:string = "http://localhost:8080/api/dashboard/newCliente"
  modificaClienteUrl:string="http://localhost:8080/api/dashboard/modificaClient"
  rinnovaIngressoClienteUrl:string= "http://localhost:8080/api/dashboard/rinnovaIngresso/"

constructor(private http:HttpClient ,  public modal:NgbModal) { }

getClienti(){
 return this.http.get(this.clientiUrl);
}
getNumberOfClienti(){
  return this.http.get<number>(this.numberOfClientiUrl)
}
getNumberOfValidClienti(){
  return this.http.get<number>(this.numberOfValidClientiUrl)
}
getNumberOfActiveClienti(){
  return this.http.get<number>(this.numberOfActiveClientiUrl)
}
getInactiveClienti(){
  return this.http.get<Icliente[]>(this.inactiveClientiUrl)
}
getAllImporti(){
  return this.http.get<ItotaleImporti>(this.totalImportiUrl)
}
getIscritti(){
  return this.http.get<ITotaleIscritti>(this.totalIscrittiUrl)
}
iscriviNewCliente(form:NgForm){
return this.http.post(this.newClienteUrl, form.value,{responseType: "text"})
}
getPageableCliente(url:string,cliente:ClienteNameDTO){
  return this.http.post<IpageableRes>(url, cliente);
}
getPageableClienteV2(url:string){
  return this.http.get<IpageableRes>(url);
}
getClienteById(url:string){
  return this.http.get<Icliente>(url)
}
modificaCliente(cliente:Icliente){
  return this.http.post<Icliente | string>(this.modificaClienteUrl,cliente )
}
rinnovaIngressoCliente(id:number, cliente:Icliente){
  return this.http.patch<Icliente>(this.rinnovaIngressoClienteUrl+id,cliente)
}
}
