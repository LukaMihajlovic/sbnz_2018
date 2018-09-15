/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SbnzTestModule } from '../../../test.module';
import { DiseaseComponent } from '../../../../../../main/webapp/app/entities/disease/disease.component';
import { DiseaseService } from '../../../../../../main/webapp/app/entities/disease/disease.service';
import { Disease } from '../../../../../../main/webapp/app/entities/disease/disease.model';

describe('Component Tests', () => {

    describe('Disease Management Component', () => {
        let comp: DiseaseComponent;
        let fixture: ComponentFixture<DiseaseComponent>;
        let service: DiseaseService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SbnzTestModule],
                declarations: [DiseaseComponent],
                providers: [
                    DiseaseService
                ]
            })
            .overrideTemplate(DiseaseComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DiseaseComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DiseaseService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Disease(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.diseases[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
