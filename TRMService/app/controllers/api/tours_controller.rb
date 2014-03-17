class Api::ToursController < ApplicationController

  #Skip authenication
  skip_before_action :verify_authenticity_token

  # POST /api/tours/create
  # => Create a tour
  def create

    tour = Tour.new(tour_params)
    if tour.save
      render :json => {:success => true, :tourid => tour.id}
    else
      render :json => {:success => false, :message => tour.errors}
    end
  end

  #POST /api/tours
  # => Get all tours of specific user include managertours and normal tours
  def gettours
    # Get all tours created by the user (The user is the manager of tours)
    manager_tours=Tour.find_by(manager_id: user_params[:id])
    # Get all tours which the user joined in
    tours=Tour.joins(:members).select("*").where("members.user_id=?", user_params[:id])
    # Return null if not found any tours
    if tours==[]
      tours=nil
    end
    render :json => {:success => true, :managerTour => manager_tours.as_json, :tour => tours.as_json}
  end

  #POST /api/tours/info
  # => Get specific tour info
  def info
    # From begin ... end => Handle exception RecordNotFound in database
    begin
      tour=Tour.find(tour_params[:id])
    rescue ActiveRecord::RecordNotFound
      tour=nil
    end
    #Return result whether found tour or not
    if tour.nil?
      render :json => {:sucess => false, :message => "Can find tour with id=#{tour_params[:id]}"}
    else
      render :json => {:sucess => true, :message => tour.as_json}
    end
  end

  #POST /api/tours/update
  def update
    begin # Handle exception not found tour
      tour=Tour.find(tour_params[:id])
    rescue ActiveRecord::RecordNotFound
      tour=nil
    end
    if tour.nil?
      render :json => {:success => false, :message => "Tour not found"}, :status => 404
    else
      if tour.update(tour_params)
        render :json => {:success => true, :message => "Tour updated"}, :status => 200
      else
        render :json => {:success => false, :message => tour.error.message}, :status => 400
      end
    end
  end

  #DELETE /api/tours/delete
  def destroy
    #TODO Method destroy: Delete the tour
  end

  #POST /api/tours/edit
  def edit
   #TODO Mothod edit: This is only alternati for update moethod
  end

  private
  # Get tour params from client
  def tour_params
    params.require(:tour).permit(:id, :title, :destination, :description, :adult_price,
                                 :child_price, :depature_date, :return_date, :manager_id,
                                 :max_members, :currency) if params[:tour]
  end

  # Get user params from client
  def user_params
    params.require(:user).permit(:id) if params[:user]
  end
end
