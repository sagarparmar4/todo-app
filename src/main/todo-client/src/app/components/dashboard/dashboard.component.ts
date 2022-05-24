import { isNgTemplate } from '@angular/compiler';
import { Component, OnInit } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { MasterService } from 'src/app/services/master.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit {
  
  itemName: string;
  itemDescription: string;
  listItems: Array<any> = []

  constructor(private masterService: MasterService, private toastrService: ToastrService) { }
  
  ngOnInit() {
    // Fetch saved ToDo items against loggedin user
    this.masterService.getToDoList().subscribe((res) => {
      this.listItems = res;
      this.toastrService.success('List fetched successfully');
    }, err => this.toastrService.error(err.message));
  }
  
  /**
   * Check or uncheck ToDO items
   * @param itemIndex 
   */
  toggleStatus(itemIndex: number){
    this.listItems[itemIndex].completed = !this.listItems[itemIndex].completed
  }

  /**
   * Append item to ToDo list
   * @param itemName 
   * @param itemDescription 
   */
  addItem(itemName: string, itemDescription: string){
    this.listItems.push({
      name: itemName,
      description: itemDescription,
      completed: false
    });
    this.itemName = null;
    this.itemDescription = null;
    this.toastrService.success('Task added successfully');
  }

  /**
   * Remove ToDo item from the list
   * @param itemIndex 
   */
  removeItem(itemIndex: number){
    this.listItems.splice(itemIndex, 1);
    this.toastrService.warning('Task removed successfully');
  }

  /**
   * Save updated ToDo items list against logged-in user
   */
  saveList() {
    this.masterService.saveToDoList(this.listItems).subscribe((res) => {
      this.listItems = res;
      this.toastrService.success('List fetched successfully');
    }, err => this.toastrService.error(err.message));
  }    
  
}
