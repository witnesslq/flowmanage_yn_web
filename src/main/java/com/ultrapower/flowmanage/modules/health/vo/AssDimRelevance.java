package com.ultrapower.flowmanage.modules.health.vo;

public class AssDimRelevance {
	
	
		public int  id;
		public String  name;
		public int  fatherId;
		public int  sceneId;
		public int lev;
		public int getLev() {
			return lev;
		}
		public void setLev(int lev) {
			this.lev = lev;
		}
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public int getFatherId() {
			return fatherId;
		}
		public void setFatherId(int fatherId) {
			this.fatherId = fatherId;
		}
		public int getSceneId() {
			return sceneId;
		}
		public void setSceneId(int sceneId) {
			this.sceneId = sceneId;
		}
		}
