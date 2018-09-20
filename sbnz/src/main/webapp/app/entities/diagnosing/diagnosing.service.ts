import { Injectable } from '@angular/core';
import {SERVER_API_URL} from "../../app.constants";
import {HttpClient} from "@angular/common/http";

@Injectable()
export class DiagnosingService {

    constructor(
        private http: HttpClient
    ) { }



}
