import { Component, OnInit } from '@angular/core';
import {ISymptom} from "../../shared/model/symptom.model";
import {SymptomService} from "../symptom/symptom.service";
import {HttpErrorResponse, HttpResponse} from "@angular/common/http";
import {Observable} from "rxjs/Observable";
import {debounceTime, map} from "rxjs/operators";
import {IDisease} from "../../shared/model/disease.model";

@Component({
  selector: 'jhi-diagnosing',
  templateUrl: './diagnosing.component.html',
  styles: []
})
export class DiagnosingComponent implements OnInit {

    databaseSymptoms: ISymptom[] = [];
    databaseDiseases: ISymptom[] = [];
    symptom: any;
    disease: any;
    enteredDiseases: IDisease[] = [];
    enteredSymptoms: ISymptom[] = [];
    constructor(
        private symptomService: SymptomService,
    ) {}

    ngOnInit() {
        console.log(this.databaseSymptoms);
        this.symptomService.query().subscribe(
            (res: HttpResponse<ISymptom[]>) => {
                for (const symp of res.body) {
                    if (!symp.spec) {
                        this.databaseSymptoms.push(symp);
                    }
                }
            }
        );
        console.log(this.databaseSymptoms);
    }


    enterSymptom(){
        if (!this.enteredSymptoms.includes(this.symptom)) {
            this.enteredSymptoms.push(this.symptom);
        }
    }

    enterDisease(){
        if (!this.enteredDiseases.includes(this.disease)) {
            this.enteredDiseases.push(this.disease);
        }
    }



    removeFromEntered(name){
        let i = 0;
        for (const symp of this.enteredSymptoms){
            if (symp.name === name){
                this.enteredSymptoms.splice(i,1);
                break;
            }
            i += 1
        }
    }

    search = (text$: Observable<string>) =>
        text$.pipe(
            debounceTime(200),
            map(
                term =>
                    term === '' ? [] : this.databaseSymptoms.filter(v => v.name.toLowerCase().indexOf(term.toLowerCase()) > -1).slice(0, 10)
            )
        );

    formatMatches = (value: any) => value.name || ''



}
