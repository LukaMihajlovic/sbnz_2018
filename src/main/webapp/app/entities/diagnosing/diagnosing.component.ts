import { Component, OnInit } from '@angular/core';
import {SymptomService} from "../symptom/symptom.service";
import {HttpErrorResponse, HttpResponse} from "@angular/common/http";
import {Observable} from "rxjs/Observable";
import {debounceTime, map} from "rxjs/operators";
import {AnamnesisService} from '../anamnesis/anamnesis.service';
import {DiseaseService} from "../disease/disease.service";
import {DrugService} from "../drug/drug.service";
import {Symptom} from "../symptom/symptom.model";
import {Disease} from "../disease/disease.model";
import {Anamnesis} from "../anamnesis/anamnesis.model";
import {Drug} from "../drug/drug.model";
import {Diagnosis} from "../diagnosis/diagnosis.model";
import {DiagnosisService} from "../diagnosis/diagnosis.service";

@Component({
  selector: 'jhi-diagnosing',
  templateUrl: './diagnosing.component.html',
  styles: [],
    styleUrls: ['diagnosing.css']
})
export class DiagnosingComponent implements OnInit {

    recovery = false;
    databaseSymptoms: Symptom[] = [];
    databaseDiseases: Disease[] = [];
    databaseAnamneses: Anamnesis[] = [];
    enteredData: Diagnosis = {};
    databaseDrugs: Drug[] = [];
    anamnesisCombo: any;
    anamnesis: any;
    retAnamnesis: any;
    symptom: any;
    disease: any;
    enteredDiseases: Disease[] = [];
    enteredSymptoms: Symptom[] = [];

    constructor(
        private diagnosisService: DiagnosisService,
        private symptomService: SymptomService,
        private anamnesisService: AnamnesisService,
        private diseaseService: DiseaseService,
        private drugService: DrugService,
    ) {}

    ngOnInit() {
        this.symptomService.query().subscribe(
            (res: HttpResponse<Symptom[]>) => {
                for (const symp of res.body) {
                    if (!symp.spec) {
                        this.databaseSymptoms.push(symp);
                    }
                }
            }
        );
        this.diseaseService.query().subscribe(
            (res: HttpResponse<Disease[]>) => {

                this.databaseDiseases = res.body;
            }
        );
        this.anamnesisService.query().subscribe(
            (res: HttpResponse<Anamnesis[]>) => {

                this.databaseAnamneses = res.body;
            }
        );
        console.log(this.databaseAnamneses.length)
        console.log(this.databaseAnamneses.length)

        this.drugService.query().subscribe(
            (res: HttpResponse<Drug[]>) => {

                this.databaseDrugs = res.body;
            }
        );

    }

    run(){
        this.enteredData.anamnesis = this.anamnesis;
        this.enteredData.symptoms = this.enteredSymptoms;
        this.enteredData.recovery = this.recovery;
        this.diagnosisService.currentDiagnosis(this.enteredData).subscribe(
            res => {
                this.retAnamnesis = res.body;
            })
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

    searchAnamnesis = (text$: Observable<string>) =>
        text$.pipe(
            debounceTime(200),
            map(
                term =>
                    term === '' ? [] : this.databaseAnamneses.filter(v => v.jmbg.toLowerCase().indexOf(term.toLowerCase()) > -1).slice(0, 10)
            )
        );



    formatMatches = (value: any) => value.name || ''
    formatMatchesAnam = (value: any) => value.jmbg || ''



}
