require 'eventmachine'
require 'json'

class Api::MessagesController < ApplicationController
  skip_before_action :verify_authenticity_token
  def create
    message= Message.new(message_params)
    message.save
    EM.run {
      client = Faye::Client.new('http://192.168.10.149:9292/bayeux')
      publication=client.publish('/message/public',
      'user_id' => message.user_id, 'fullname' => message.user.fullname, 'content' => message.content)
      publication.callback do
        EM.stop
        render :json => {:success => true}
      end
      publication.errback do |error|
        puts "[PUBLISH FAILED] #{error.inspect}"
        EM.stop
      end
      client.bind 'transport:down' do
        render :json => {:message => "[CONNECTION DOWN]"} 
        EM.stop
      end
    }
  end

  def index
    #Get all message from 5 days ago to now
    messages=Message.joins(:user).select("users.id,users.fullname,messages.content,messages.created_at").where("DAY(messages.
      created_at) >= ? ",Time.now.day-5).order("messages.id ASC")
    render :json => messages.as_json
  end
  private

  def message_params
    params.require(:message).permit(:user_id, :content) if params[:message]
  end
end
