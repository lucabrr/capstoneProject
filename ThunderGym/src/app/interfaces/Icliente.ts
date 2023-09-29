export interface Icliente {

        id?:number,
        nome:string,
        cognome:string,
        nomeCompleto?:string
        dataDiNascita: Date,
        email:string,
        numeroTelefono:string,
        dataIscrizione?: Date,
        dataUltimoIngresso?:Date,
        ingresso: number

}
